package com.elvitalya.notes.presentation.pin_code

import android.Manifest
import android.app.Application
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.app.ActivityCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.notes.presentation.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinCodeViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    var authSuccess by mutableStateOf(false)

    private var cancellationSignal: CancellationSignal? = null

    private val authCallBack: BiometricPrompt.AuthenticationCallback =
        @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                authSuccess = true
            }
        }

    companion object {
        private val PIN = stringPreferencesKey("PIN")
    }

    val pinCode = mutableStateOf(TextFieldValue(""))

    var pinEmpty = mutableStateOf(false)

    private var savedPin = ""

    private val dataStore = application.applicationContext.dataStore

    init {
        application.applicationContext.dataStore.data.onEach {
            pinEmpty.value = it[PIN] == null
            savedPin = it[PIN] ?: ""
        }.launchIn(viewModelScope)
    }

    fun savePin() {
        viewModelScope.launch {
            dataStore.edit {
                it[PIN] = pinCode.value.text

            }
        }
    }

    fun enterPin(): Boolean {
        return if (pinCode.value.text == savedPin) {
            true
        } else {
            Toast.makeText(application.applicationContext, "Пин код не верный", Toast.LENGTH_SHORT)
                .show()
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        val keyGuardManager =
            application.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyGuardManager.isDeviceSecure) return true

        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.USE_BIOMETRIC)
            != PackageManager.PERMISSION_GRANTED
        ) return false

        return application.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun launchBiometric() {
        if (checkBiometricSupport()) {
            val biometricPrompt = BiometricPrompt.Builder(application)
                .apply {
                    setTitle("Авторизация")
                    setConfirmationRequired(false)
                    setNegativeButton("Отмена", application.mainExecutor) { _, _ ->
                        Toast.makeText(application, "Отмена", Toast.LENGTH_SHORT)
                            .show()
                    }
                }.build()
            biometricPrompt.authenticate(
                getCancellationSignal(),
                application.mainExecutor,
                authCallBack
            )
        }
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(application, "Auth cancelled", Toast.LENGTH_SHORT).show()
        }
        return cancellationSignal as CancellationSignal
    }
}
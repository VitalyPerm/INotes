package com.elvitalya.notes.presentation.pin_code

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
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
}
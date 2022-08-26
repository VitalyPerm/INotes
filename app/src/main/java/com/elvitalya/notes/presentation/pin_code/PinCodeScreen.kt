package com.elvitalya.notes.presentation.pin_code

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elvitalya.notes.R
import com.elvitalya.notes.theme.theme.CardBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PinCodeScreen(
    viewModel: PinCodeViewModel = hiltViewModel(),
    goForward: () -> Unit
) {
    val textFieldColor = TextFieldDefaults.textFieldColors(
        textColor = Color.White,
        disabledTextColor = Color.White,
        cursorColor = Color.White,
        focusedIndicatorColor = CardBackground,
        unfocusedIndicatorColor = CardBackground,
        backgroundColor = CardBackground
    )

    if(viewModel.authSuccess) goForward()

    var passVisibility by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scope.launch {
            delay(300)
            focusRequester.requestFocus()
        }
    }

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = stringResource(
                id = if (viewModel.pinEmpty.value) R.string.pin_code_new
                else R.string.pin_code_enter
            ),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .clip(RoundedCornerShape(16.dp))
                .focusRequester(focusRequester),
            value = viewModel.pinCode.value,
            trailingIcon = {
                IconButton(
                    onClick = {
                        passVisibility = !passVisibility
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.password_eye),
                        contentDescription = null,
                        tint = if (passVisibility) Color.White else Color.Black
                    )
                }
            },
            visualTransformation = if (passVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            onValueChange = {
                if (it.text.length < 5) {
                    viewModel.pinCode.value = it
                }
                if (it.text.length == 4 && !viewModel.pinEmpty.value) {
                    val pinValid = viewModel.enterPin()
                    if (pinValid) goForward()
                }
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            colors = textFieldColor,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.NumberPassword
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (viewModel.pinEmpty.value) {
                        viewModel.savePin()
                        goForward()
                    } else {
                        val pinValid = viewModel.enterPin()
                        if (pinValid) goForward()
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = stringResource(R.string.pin_code_use_biometry),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_fingerprint),
            contentDescription = "fingerPrint",
            modifier = Modifier
            .clickable {
                viewModel.launchBiometric()
            }
        )
    }
}
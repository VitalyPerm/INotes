package com.elvitalya.notes.presentation.pin_code

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavController
import com.elvitalya.notes.R
import com.elvitalya.notes.ui.theme.CardBackground

@Composable
fun PinCodeScreen(
    viewModel: PinCodeViewModel = hiltViewModel(),
    navController: NavController
) {
    val textFieldColor = TextFieldDefaults.textFieldColors(
        textColor = Color.White,
        disabledTextColor = Color.White,
        cursorColor = Color.White,
        focusedIndicatorColor = CardBackground,
        unfocusedIndicatorColor = CardBackground,
        backgroundColor = CardBackground
    )

    var passVisibility by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(200.dp))

        Text(text = stringResource(id = if (viewModel.pinEmpty.value) R.string.pin_code_new else R.string.pin_code_enter))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .clip(RoundedCornerShape(16.dp)),
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
                        viewModel.savePin(navController)
                    } else {
                        viewModel.enterPin(navController)
                    }
                }
            )
        )
    }
}
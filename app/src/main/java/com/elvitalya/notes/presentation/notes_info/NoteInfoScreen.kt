package com.elvitalya.notes.presentation.notes_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elvitalya.notes.R
import com.elvitalya.notes.presentation.TopBar
import com.elvitalya.notes.theme.theme.CardBackground
import com.google.accompanist.insets.navigationBarsPadding

const val TAG = "note_info"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteInfoScreen(
    viewModel: NotesInfoScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val textFieldColor = TextFieldDefaults.textFieldColors(
        textColor = Color.White,
        disabledTextColor = Color.White,
        cursorColor = Color.White,
        focusedIndicatorColor = CardBackground,
        unfocusedIndicatorColor = CardBackground,
        backgroundColor = CardBackground
    )


    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = 16.dp)
    ) {
        TopBar(
            title = stringResource(id = R.string.note_detail_title),
            actions = {
                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteInfoEvent.Insert)
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = viewModel.state.title,
            onValueChange = {
                viewModel.onEvent(NoteInfoEvent.TitleChanged(it))
            },
            colors = textFieldColor,
            placeholder = {
                Text(text = "Название")
            },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .navigationBarsPadding()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(16.dp),
            value = viewModel.state.description,
            onValueChange = {
                viewModel.onEvent(NoteInfoEvent.DescriptionChanged(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.desc))
            },
            colors = textFieldColor
        )
    }
}



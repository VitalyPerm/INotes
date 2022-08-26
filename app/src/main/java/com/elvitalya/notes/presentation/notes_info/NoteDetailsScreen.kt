package com.elvitalya.notes.presentation.notes_info

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elvitalya.notes.R
import com.elvitalya.notes.presentation.main.NEW_NOTE
import com.elvitalya.notes.theme.theme.CardBackground
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding


@Composable
fun NoteDetails(
    goBack: () -> Unit,
    popBackStack: () -> Unit,
    noteId: Int?,
    getNoteDetails: (Int) -> Unit,
    insertNote: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescChanged: (String) -> Unit,
    title: String,
    description: String,
) {
    BackHandler {
        goBack()
        popBackStack()
    }

    LaunchedEffect(key1 = true) {
        getNoteDetails(noteId ?: NEW_NOTE)
    }

    val textFieldColor = TextFieldDefaults.textFieldColors(
        textColor = Color.White,
        disabledTextColor = Color.White,
        cursorColor = Color.White,
        focusedIndicatorColor = CardBackground,
        unfocusedIndicatorColor = CardBackground,
        backgroundColor = CardBackground
    )

    ProvideWindowInsets {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsWithImePadding()
        ) {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.secondary,
                title = {
                    Text(
                        text = stringResource(id = R.string.note_detail_title),
                        modifier = Modifier
                            .padding(start = 12.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            insertNote()
                            goBack()
                            popBackStack()
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
                value = title,
                onValueChange = {
                    onTitleChanged(it)
                },
                colors = textFieldColor,
                placeholder = {
                    Text(text = "Название")
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                shape = RoundedCornerShape(16.dp),
                value = description,
                onValueChange = {
                    onDescChanged(it)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.desc))
                },
                colors = textFieldColor
            )

        }
    }
}

@Composable
fun NoteDetailsScreen(
    viewModel: NotesDetailsScreenViewModel = hiltViewModel(),
    navController: NavController,
    noteId: Int?,
    goBack: () -> Unit,
) {
    NoteDetails(
        goBack = goBack,
        popBackStack = { navController.popBackStack() },
        noteId = noteId,
        getNoteDetails = { viewModel.onEvent(NoteDetailsEvent.GetNoteDetails(it)) },
        insertNote = { viewModel.onEvent(NoteDetailsEvent.Insert) },
        onTitleChanged = { viewModel.onEvent(NoteDetailsEvent.TitleChanged(it)) },
        onDescChanged = { viewModel.onEvent(NoteDetailsEvent.DescriptionChanged(it)) },
        title = viewModel.state.title,
        description = viewModel.state.description
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NoteDetailsPreview() {
    NoteDetails(
        goBack = { },
        popBackStack = { },
        noteId = 1,
        getNoteDetails = {},
        insertNote = { },
        onTitleChanged = {},
        onDescChanged = {},
        title = "Hello",
        description = "Desc"
    )
}





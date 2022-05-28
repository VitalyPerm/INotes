package com.elvitalya.notes.presentation.notes_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elvitalya.notes.R
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.presentation.TopBar
import com.elvitalya.notes.ui.theme.CardBackground
import com.google.accompanist.insets.navigationBarsPadding
import kotlin.random.Random

@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.note_list_title),
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(NotesListEvent.NewNote(navController))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add",
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier
                                .padding(12.dp)
                        )
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .navigationBarsPadding()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.notes.size) { i ->
                val note = state.notes[i]

                NoteItem(
                    note = note,
                    onClickDelete = {
                        viewModel.onEvent(
                            NotesListEvent.Delete(note = note)
                        )
                    },
                    onClick = {
                        viewModel.onEvent(NotesListEvent.UpdateNote(note, navController))
                    }
                )
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onClickDelete: (Note) -> Unit = {},
    onClick: (Note) -> Unit = {}
) {

    var dialogState by remember {
        mutableStateOf(false)
    }

    DeleteNoteAlertDialog(
        dialogState = dialogState,
        onDismissRequest = {
            dialogState = !it
        },
        onClickYes = { onClickDelete(note) })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(CardBackground)
            .clickable { onClick.invoke(note) }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = note.title,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Center),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                onClick = { dialogState = true }
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = Color.White
                )
            }
        }



        Divider(color = Color.Black)

        Text(
            text = note.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 16.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )

        Text(
            text = note.date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 12.sp,
            textAlign = TextAlign.End,
            color = Color.White
        )
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    LazyColumn{
        items(10) {
            NoteItem(
                note = Note(
                    id = Random.nextInt(),
                    title = "Title",
                    description = "tut koroche description",
                    date = "22/02/2222 15:55"
                )
            )
        }
    }

}
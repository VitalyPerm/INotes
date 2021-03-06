package com.elvitalya.notes.presentation.notes_list

import androidx.compose.foundation.Image
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
import com.elvitalya.notes.R
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.presentation.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlin.random.Random

@Composable
@RootNavGraph(start = true)
@Destination
fun NotesListScreen(
    viewModel: NotesListViewModel = hiltViewModel(),
) {

    val state = viewModel.state

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.note_list_title),
                actions = {
                    IconButton(onClick = {
                      //  navigator.navigate(NoteInfoScreenDestination(Note()))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add",
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier
                                .padding(end = 12.dp)
                        )
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.primary
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
                       // navigator.navigate(NoteInfoScreenDestination(it))
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
            .background(MaterialTheme.colors.background)
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
                    .padding(start = 16.dp, end = 55.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.primary,
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
                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete"
                )
            }
        }



        Divider(color = Color.Red)

        Text(
            text = note.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 16.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = note.date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 12.sp,
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.primary
        )
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    NoteItem(
        note = Note(
            id = Random.nextInt(),
            title = "Proverkahghhghghghghghghghghghghghghghghghghg",
            description = "tut koroche description",
            date = "22/02/2222 15:55"
        )
    )
}
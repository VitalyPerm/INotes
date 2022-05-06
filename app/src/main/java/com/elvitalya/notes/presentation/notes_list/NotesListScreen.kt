package com.elvitalya.notes.presentation.notes_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.presentation.destinations.NoteInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.random.Random

@Composable
@RootNavGraph(start = true)
@Destination
fun NotesListScreen(
    viewModel: NotesListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    DisposableEffect(key1 = viewModel) {
        viewModel.fetchNoteList()
        onDispose { viewModel.stop() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "AppBar") },
                backgroundColor = Color.White,
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(NoteInfoScreenDestination())
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.notes.size) { i ->
                val note = state.notes[i]

                NoteItem(
                    note = note, onClickDelete = {
                        viewModel.onEvent(
                            NotesListEvent.Delete(note = note)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onClickDelete: (Note) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Yellow)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = note.title,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                onClick = { onClickDelete.invoke(note) }
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
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = note.date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 12.sp,
            textAlign = TextAlign.End
        )
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    NoteItem(
        note = Note(
            id = Random.nextInt(),
            title = "Proverka",
            description = "tut koroche description",
            date = "22/02/2222 15:55"
        )
    )
}
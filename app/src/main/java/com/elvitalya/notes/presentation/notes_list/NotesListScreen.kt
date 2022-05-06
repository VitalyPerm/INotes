package com.elvitalya.notes.presentation.notes_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.presentation.destinations.NoteInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@RootNavGraph(start = true)
@Destination
fun NotesListScreen(
    viewModel: NotesListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

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

                NoteItem(note = note)


            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = note.title,
            modifier = Modifier
                .padding(32.dp)
        )

        Text(
            text = note.description,
            modifier = Modifier
                .padding(32.dp)
        )

        Text(
            text = note.date,
            modifier = Modifier
                .padding(32.dp)
        )
    }

}
package com.elvitalya.notes.presentation.notes_info

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elvitalya.notes.ui.theme.Purple700
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


const val TAG = "note_info"

@Composable
@Destination
fun NoteInfoScreen(
    viewModel: NotesInfoScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    var state = viewModel.state

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.title,
                onValueChange = {
                    Log.d(TAG, "NoteInfoScreen: onValueChange $it")
                    viewModel.onEvent(NoteInfoEvent.TitleChanged(it))
                },
                textStyle = TextStyle(
                    color = Purple700
                ),
                placeholder = {
                    Text(text = "Название")
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = viewModel.state.description,
                onValueChange = {
                    viewModel.onEvent(NoteInfoEvent.DescriptionChanged(it))
                },
                placeholder = {
                    Text(text = "Содержание")
                })
        }

        Button(
            onClick = {
                viewModel.onEvent(NoteInfoEvent.Insert)
                navigator.navigateUp()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "Save")
        }
    }

}



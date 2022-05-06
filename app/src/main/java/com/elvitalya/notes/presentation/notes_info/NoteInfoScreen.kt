package com.elvitalya.notes.presentation.notes_info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.util.format
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*

@Composable
@Destination
fun NoteInfoScreen(
    viewModel: NotesInfoScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    Column(Modifier.fillMaxSize()) {
        Text(text = "Hello", modifier = Modifier.clickable {
            viewModel.insert(Note("CHECK", "CHECK", Date().format()))
            navigator.navigateUp()
        })
    }
}
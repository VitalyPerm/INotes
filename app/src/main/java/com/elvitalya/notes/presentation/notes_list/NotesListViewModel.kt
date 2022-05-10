package com.elvitalya.notes.presentation.notes_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.elvitalya.notes.Screens
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.presentation.SharedNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val sharedNote: SharedNote
) : ViewModel() {

    private var deleteNoteJob: Job? = null

    var state by mutableStateOf(NotesListState())

    private fun delete(note: Note) {
        deleteNoteJob?.cancel()
        deleteNoteJob = viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun onEvent(event: NotesListEvent) {
        when (event) {
            is NotesListEvent.Delete -> {
                delete(event.note)
            }
            is NotesListEvent.UpdateNote -> {
                sharedNote.note = event.note
                event.navController.navigate(Screens.Details.route)
            }

            is NotesListEvent.NewNote -> {
                sharedNote.note = null
                event.navController.navigate(Screens.Details.route)
            }
        }
    }

    private fun getNotes() {
        repository.getNotes().onEach {
            state = state.copy(notes = it)
        }.launchIn(viewModelScope)
    }

    init {
        getNotes()
    }
}
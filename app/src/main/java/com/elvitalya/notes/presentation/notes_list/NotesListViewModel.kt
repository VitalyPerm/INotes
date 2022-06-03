package com.elvitalya.notes.presentation.notes_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.notes.presentation.Screens
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.util.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NEW_NOTE = 0

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NotesRepository,
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
                event.navController.navigate("${Screens.Details.route}/${event.noteId}")
            }

            is NotesListEvent.NewNote -> {
                event.navController.navigate("${Screens.Details.route}/$NEW_NOTE")
            }
        }
    }

    init {
        repository.getNotes().onEach {
            state = state.copy(notes = it.sortedByDescending { note -> note.date.toDate() })
        }.launchIn(viewModelScope)
    }
}
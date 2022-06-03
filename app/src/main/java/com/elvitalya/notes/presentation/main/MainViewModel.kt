package com.elvitalya.notes.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MainViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    private var deleteNoteJob: Job? = null
    private var updateNoteJob: Job? = null

    var state = mutableStateListOf<Note>()

    private fun delete(note: Note) {
        deleteNoteJob?.cancel()
        deleteNoteJob = viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    private fun update(note: Note) {
        updateNoteJob?.cancel()
        updateNoteJob = viewModelScope.launch {
            repository.updateNote(note.copy(favorite = !note.favorite))
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.Delete -> {
                delete(event.note)
            }
            is MainEvent.UpdateNote -> {
                event.navController.navigate("${Screens.Details.route}/${event.noteId}")
            }

            is MainEvent.NewNote -> {
                event.navController.navigate("${Screens.Details.route}/$NEW_NOTE")
            }

            is MainEvent.OnClickFavorite -> {
                update(event.note)
            }
        }
    }

    init {
        repository.getNotes().onEach {
            state.clear()
            state.addAll(it)
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        updateNoteJob?.cancel()
        deleteNoteJob?.cancel()
    }
}
package com.elvitalya.notes.presentation.notes_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private var getNotesJob: Job? = null

    private var deleteNoteJob: Job? = null

    var state by mutableStateOf(NotesListState())

    private fun delete(note: Note) {
        deleteNoteJob?.cancel()
        deleteNoteJob = viewModelScope.launch {
            repository.deleteNote(note)
            fetchNoteList()
        }
    }

    fun onEvent(event: NotesListEvent) {
        when (event) {
            is NotesListEvent.Delete -> {
                delete(event.note)
            }
        }
    }

    fun fetchNoteList() {
        getNotesJob?.cancel()
        getNotesJob = viewModelScope.launch {
            repository.getNotes()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(
                                    notes = it
                                )
                            }
                        }
                        else -> {

                        }
                    }
                }
        }
    }

    fun stop() {
        getNotesJob?.cancel()
        deleteNoteJob?.cancel()
    }
}
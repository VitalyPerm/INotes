package com.elvitalya.notes.presentation.notes_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.presentation.main.NEW_NOTE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesDetailsScreenViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    var state by mutableStateOf(Note())

    fun onEvent(event: NoteDetailsEvent) {
        when (event) {
            is NoteDetailsEvent.Insert -> {
                viewModelScope.launch {
                    repository.addNote(state)
                }
            }
            is NoteDetailsEvent.TitleChanged -> {
                state = state.copy(title = event.value)
            }

            is NoteDetailsEvent.DescriptionChanged -> {
                state = state.copy(description = event.value)
            }

            is NoteDetailsEvent.GetNoteDetails -> {
                if (event.value != NEW_NOTE) {
                    viewModelScope.launch {
                        val note = repository.getNote(event.value)
                        state = note
                    }
                }
            }
        }
    }
}
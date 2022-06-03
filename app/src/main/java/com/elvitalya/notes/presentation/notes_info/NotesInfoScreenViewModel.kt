package com.elvitalya.notes.presentation.notes_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.presentation.notes_list.NEW_NOTE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesInfoScreenViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    var state by mutableStateOf(Note())

    fun onEvent(event: NoteInfoEvent) {
        when (event) {
            is NoteInfoEvent.Insert -> {
                viewModelScope.launch {
                    repository.addNote(state)
                }
            }
            is NoteInfoEvent.TitleChanged -> {
                state = state.copy(title = event.value)
            }

            is NoteInfoEvent.DescriptionChanged -> {
                state = state.copy(description = event.value)
            }

            is NoteInfoEvent.GetNoteInfo -> {
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
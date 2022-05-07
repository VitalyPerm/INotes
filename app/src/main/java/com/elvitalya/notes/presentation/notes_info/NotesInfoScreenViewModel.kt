package com.elvitalya.notes.presentation.notes_info

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.util.format
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotesInfoScreenViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    var state by mutableStateOf(NoteInfoState())

    private fun insert(note: Note) {
        viewModelScope.launch {
            repository.addNote(note)
            Log.d(TAG, "insert: $note")
        }
    }

    fun onEvent(event: NoteInfoEvent) {
        when (event) {
            is NoteInfoEvent.Insert -> {
                insert(
                    Note(
                        id = Random().nextInt(),
                        title = state.title,
                        description = state.description,
                        date = Date().format()
                    )
                )
            }
            is NoteInfoEvent.TitleChanged -> {
                state = state.copy(title = event.value)
            }

            is NoteInfoEvent.DescriptionChanged -> {
                state = state.copy(description = event.value)
            }
        }
    }
}
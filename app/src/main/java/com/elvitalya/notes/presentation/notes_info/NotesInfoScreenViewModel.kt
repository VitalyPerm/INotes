package com.elvitalya.notes.presentation.notes_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesInfoScreenViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel(){

   // var state by mutableStateOf(NoteInfoState())

    fun insert(note: Note) {
        viewModelScope.launch {
            repository.addNote(note)
        }
    }
}
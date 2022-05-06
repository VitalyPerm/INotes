package com.elvitalya.notes.presentation.notes_list

import com.elvitalya.notes.domain.model.Note

sealed class NotesListEvent {

    data class Delete(val note: Note): NotesListEvent()

}
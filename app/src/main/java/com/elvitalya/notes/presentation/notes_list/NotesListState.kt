package com.elvitalya.notes.presentation.notes_list

import com.elvitalya.notes.domain.model.Note

data class NotesListState(
    val notes: List<Note> = emptyList()
)
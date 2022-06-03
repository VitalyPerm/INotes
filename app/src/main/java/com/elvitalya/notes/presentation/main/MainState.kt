package com.elvitalya.notes.presentation.main

import com.elvitalya.notes.domain.model.Note

data class MainState(
    val notes: List<Note> = emptyList(),
    val favorites: List<Note> = emptyList()
)
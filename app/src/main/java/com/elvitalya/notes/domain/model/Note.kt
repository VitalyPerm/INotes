package com.elvitalya.notes.domain.model

import java.util.*

data class Note(
    val id: Int,
    val title: String,
    val description: String,
    val date: String
)
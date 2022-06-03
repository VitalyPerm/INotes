package com.elvitalya.notes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class NoteEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val date: Date,
    val favorite: Boolean
)
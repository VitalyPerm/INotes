package com.elvitalya.notes.domain.repository

import com.elvitalya.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNote(id: Int): Note

    suspend fun updateNote(note: Note)

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)

}
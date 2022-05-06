package com.elvitalya.notes.domain.repository

import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.util.Resource
import kotlinx.coroutines.flow.Flow
import java.util.*

interface NotesRepository {

    suspend fun getNotes(): Flow<Resource<List<Note>>>

    suspend fun getNote(id: Int): Resource<Note>

    suspend fun updateNote(note: Note)

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)

}
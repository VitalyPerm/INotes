package com.elvitalya.notes.data.repository

import com.elvitalya.notes.data.local.NotesDataSource
import com.elvitalya.notes.data.mappers.toNote
import com.elvitalya.notes.data.mappers.toNoteEntity
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepositoryImpl @Inject constructor(
    private val notesDataSource: NotesDataSource
) : NotesRepository {

    override fun getNotes(): Flow<List<Note>> {
        return notesDataSource.getNotes().map { it.toNote() }
    }

    override suspend fun getNote(id: Int): Resource<Note> = withContext(Dispatchers.IO) {
        return@withContext Resource.Success(data = notesDataSource.getNote(id).toNote())
    }

    override suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        notesDataSource.updateNote(note.toNoteEntity())
    }

    override suspend fun addNote(note: Note) = withContext(Dispatchers.IO) {
        notesDataSource.addNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO) {
        notesDataSource.deleteNote(note.toNoteEntity())
    }
}
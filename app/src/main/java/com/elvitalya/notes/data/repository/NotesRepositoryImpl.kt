package com.elvitalya.notes.data.repository

import com.elvitalya.notes.data.local.NotesDataBase
import com.elvitalya.notes.data.mappers.toNote
import com.elvitalya.notes.data.mappers.toNoteEntity
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepositoryImpl @Inject constructor(
    db: NotesDataBase
) : NotesRepository {

    private val dao = db.dao

    override suspend fun getNotes(): Flow<Resource<List<Note>>> {
        return flow {

            emit(Resource.Loading(true))

            emit(Resource.Success(
                data = dao.getNotes()
                    .map { it.toNote() }
            ))

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getNote(id: UUID): Resource<Note> = withContext(Dispatchers.IO) {
        return@withContext Resource.Success(data = dao.getNote(id).toNote())
    }

    override suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        dao.updateNote(note.toNoteEntity())
    }

    override suspend fun addNote(note: Note) = withContext(Dispatchers.IO) {
        dao.addNote(note.toNoteEntity())
    }
}
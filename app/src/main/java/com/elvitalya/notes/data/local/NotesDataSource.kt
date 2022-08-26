package com.elvitalya.notes.data.local

import kotlinx.coroutines.flow.map
import javax.inject.Inject


class NotesDataSource @Inject constructor(
    private val dao: NotesDao
) {

    fun getNotes() = dao.getNotes().map { it.sortedByDescending { note -> note.date } }

    suspend fun getNote(id: Int) = dao.getNote(id)

    suspend fun updateNote(note: NoteEntity) = dao.updateNote(note)

    suspend fun addNote(note: NoteEntity) = dao.addNote(note)

    suspend fun deleteNote(note: NoteEntity) = dao.deleteNote(note)

}
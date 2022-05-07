package com.elvitalya.notes.data.local

import javax.inject.Inject


class NotesDataSource @Inject constructor(
    private val dao: NotesDao
) {

    fun getNotes() = dao.getNotes()

    suspend fun getNote(id: Int) = dao.getNote(id)

    suspend fun updateNote(note: NoteEntity) = dao.updateNote(note)

    suspend fun addNote(note: NoteEntity) = dao.addNote(note)

    suspend fun deleteNote(note: NoteEntity) = dao.deleteNote(note)

}
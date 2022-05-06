package com.elvitalya.notes.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.elvitalya.notes.domain.model.Note
import java.util.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM noteentity")
    suspend fun getNotes(): List<NoteEntity>


    @Query("SELECT * FROM noteentity WHERE id=(:id)")
    suspend fun getNote(id: Int): NoteEntity

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

}
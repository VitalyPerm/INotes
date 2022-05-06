package com.elvitalya.notes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM noteentity")
    suspend fun getNotes(): List<NoteEntity>


    @Query("SELECT * FROM noteentity WHERE id=(:id)")
    suspend fun getNote(id: UUID): NoteEntity

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addNote(note: NoteEntity)

}
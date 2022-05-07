package com.elvitalya.notes.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM noteentity")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM noteentity WHERE id=(:id)")
    suspend fun getNote(id: Int): NoteEntity

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

}
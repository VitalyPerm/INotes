package com.elvitalya.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [NoteEntity::class],
    version = 1
)
@TypeConverters(NotesTypeConverter::class)
abstract class NotesDataBase : RoomDatabase() {
    abstract val dao: NotesDao
}
package com.elvitalya.notes.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [NoteEntity::class],
    version = 2
)
@TypeConverters(NotesTypeConverter::class)
abstract class NotesDataBase : RoomDatabase() {
    abstract val dao: NotesDao
}
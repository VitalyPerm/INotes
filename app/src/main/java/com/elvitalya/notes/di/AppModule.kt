package com.elvitalya.notes.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.elvitalya.notes.data.local.NotesDao
import com.elvitalya.notes.data.local.NotesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNotesDao(app: Application): NotesDao {
        return Room.databaseBuilder(
            app,
            NotesDataBase::class.java,
            "notes_db"
        )
            .addMigrations(object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE NoteEntity ADD COLUMN favorite INTEGER DEFAULT 0 NOT NULL")
                }
            }
            )
            .build().dao
    }
}
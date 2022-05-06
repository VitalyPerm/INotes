package com.elvitalya.notes.di

import android.app.Application
import androidx.room.Room
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
    fun provideNotesDatabase(app: Application): NotesDataBase {
        return Room.databaseBuilder(
            app,
            NotesDataBase::class.java,
            "notes_db"
        ).build()
    }
}
package com.elvitalya.notes.di

import com.elvitalya.notes.data.repository.NotesRepositoryImpl
import com.elvitalya.notes.domain.repository.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        notesRepositoryImpl: NotesRepositoryImpl
    ): NotesRepository
}
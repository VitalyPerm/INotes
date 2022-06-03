package com.elvitalya.notes.presentation.main

import androidx.navigation.NavController
import com.elvitalya.notes.domain.model.Note

sealed class MainEvent {

    data class Delete(val note: Note) : MainEvent()

    data class UpdateNote(val navController: NavController, val noteId: Int) :
        MainEvent()

    data class NewNote(val navController: NavController) : MainEvent()

}
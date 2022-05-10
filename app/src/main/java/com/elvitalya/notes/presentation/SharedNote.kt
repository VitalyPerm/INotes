package com.elvitalya.notes.presentation

import com.elvitalya.notes.domain.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedNote @Inject constructor() {

    var note: Note? = null

}
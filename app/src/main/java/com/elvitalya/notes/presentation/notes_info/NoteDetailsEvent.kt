package com.elvitalya.notes.presentation.notes_info

sealed class NoteDetailsEvent {
    object Insert : NoteDetailsEvent()

    data class TitleChanged(val value: String) : NoteDetailsEvent()

    data class DescriptionChanged(val value: String) : NoteDetailsEvent()

    data class GetNoteDetails(val value: Int) : NoteDetailsEvent()
}
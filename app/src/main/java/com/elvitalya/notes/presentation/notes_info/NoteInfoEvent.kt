package com.elvitalya.notes.presentation.notes_info

sealed class NoteInfoEvent {
    object Insert : NoteInfoEvent()

    data class TitleChanged(val value: String): NoteInfoEvent()

    data class DescriptionChanged(val value: String): NoteInfoEvent()
}
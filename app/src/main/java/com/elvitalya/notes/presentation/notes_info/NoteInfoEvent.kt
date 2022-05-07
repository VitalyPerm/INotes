package com.elvitalya.notes.presentation.notes_info

import com.elvitalya.notes.domain.model.Note

sealed class NoteInfoEvent {
    object Insert : NoteInfoEvent()

    data class TitleChanged(val value: String): NoteInfoEvent()

    data class DescriptionChanged(val value: String): NoteInfoEvent()

    data class GetDataFromArgs(val value: Note): NoteInfoEvent()
}
package com.elvitalya.notes.data.mappers

import com.elvitalya.notes.data.local.NoteEntity
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.util.format
import com.elvitalya.notes.util.toDate


fun NoteEntity.toNote(): Note =
    Note(
        id = id,
        title = title,
        description = description,
        date = date.format()
    )


fun Note.toNoteEntity(): NoteEntity =
    NoteEntity(
        id = id,
        title = title,
        description = description,
        date = date.toDate()
    )




package com.elvitalya.notes.domain.model


import android.os.Parcelable
import com.elvitalya.notes.util.format
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: Int = Random().nextInt(),
    val title: String = "",
    val description: String = "",
    val date: String = Date().format()
): Parcelable
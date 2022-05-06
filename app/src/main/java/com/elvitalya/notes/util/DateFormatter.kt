package com.elvitalya.notes.util

import java.text.SimpleDateFormat
import java.util.*


fun Date.format(): String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(this)

fun String.toDate(): Date =
    (SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(this) ?: Date())

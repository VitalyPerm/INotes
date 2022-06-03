package com.elvitalya.notes.presentation.notes_list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.notes.domain.model.Note
import com.elvitalya.notes.theme.theme.CardBackground
import kotlin.random.Random

@Composable
fun NotesListScreen(
    notes: List<Note>,
    favorite: Boolean,
    onClickNote: (Note) -> Unit,
    onClickDelete: (Note) -> Unit,
    onClickFavorite: (Note) -> Unit
) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        val list = if (favorite) notes.filter { it.favorite } else notes

        items(list) { note ->
            NoteItem(
                note = note,
                onClickDelete = {
                    onClickDelete(it)
                },
                onClick = {
                    onClickNote(it)
                },
                onClickFavorite = {
                    onClickFavorite(it)
                }
            )
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onClickDelete: (Note) -> Unit = {},
    onClick: (Note) -> Unit = {},
    onClickFavorite: (Note) -> Unit = {}
) {

    var dialogState by remember {
        mutableStateOf(false)
    }


    val cardBgColor by animateColorAsState(
        targetValue = if (note.favorite) Color.White
        else CardBackground, animationSpec = tween(1000)
    )

    val restColor by animateColorAsState(
        targetValue = if (note.favorite) Color.Black else Color.White,
        animationSpec = tween(1000)
    )

    DeleteNoteAlertDialog(
        dialogState = dialogState,
        onDismissRequest = {
            dialogState = !it
        },
        onClickYes = { onClickDelete(note) })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(cardBgColor)
            .clickable { onClick.invoke(note) }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { onClickFavorite(note) }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "delete",
                    tint = if (note.favorite) Color.Red else Color.White
                )
            }

            Text(
                text = note.title,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .weight(1f),
                color = restColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = { dialogState = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = restColor
                )
            }
        }



        Divider(color = Color.Black)

        Text(
            text = note.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 16.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = restColor
        )

        Text(
            text = note.date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 12.sp,
            textAlign = TextAlign.End,
            color = restColor
        )
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    LazyColumn {
        items(10) {
            NoteItem(
                note = Note(
                    id = Random.nextInt(),
                    title = "Title",
                    description = "tut koroche description",
                    date = "22/02/2222 15:55"
                )
            )
        }
    }

}
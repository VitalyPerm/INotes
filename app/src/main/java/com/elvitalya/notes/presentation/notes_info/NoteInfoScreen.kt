package com.elvitalya.notes.presentation.notes_info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elvitalya.notes.R
import com.elvitalya.notes.presentation.TopBar
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.ramcosta.composedestinations.annotation.Destination


const val TAG = "note_info"

@Composable
@Destination
fun NoteInfoScreen(
    viewModel: NotesInfoScreenViewModel = hiltViewModel(),
) {
    
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.note_detail_title))
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = viewModel.state.title,
                    onValueChange = {
                        viewModel.onEvent(NoteInfoEvent.TitleChanged(it))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.background,
                        placeholderColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                        textColor = MaterialTheme.colors.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Red,
                        disabledIndicatorColor = Color.LightGray

                    ),
                    placeholder = {
                        Text(text = "Название")
                    }
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f)
                        .navigationBarsWithImePadding()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    value = viewModel.state.description,
                    onValueChange = {
                        viewModel.onEvent(NoteInfoEvent.DescriptionChanged(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.desc))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.background,
                        placeholderColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                        textColor = MaterialTheme.colors.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Red,
                        disabledIndicatorColor = Color.LightGray

                    )
                )
            }

            Box(
                modifier = Modifier
                    .navigationBarsWithImePadding()
                    .padding(bottom = 16.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.background)
                    .clickable {
                        viewModel.onEvent(NoteInfoEvent.Insert)

                    }
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}



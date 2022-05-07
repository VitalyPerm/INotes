package com.elvitalya.notes.presentation.notes_list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.elvitalya.notes.R

@Composable
fun DeleteNoteAlertDialog(
    dialogState: Boolean,
    onDismissRequest: (delete: Boolean) -> Unit,
    onClickYes: () -> Unit
) {
    if (dialogState) {
        AlertDialog(
            backgroundColor = Color.White,
            onDismissRequest = {
                onDismissRequest(dialogState)
            },
            title = null,
            text = null,
            buttons = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.alert_dialog_delete_title),
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF113042),
                        fontSize = 18.sp,
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        val context = LocalContext.current

                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                Toast
                                    .makeText(
                                        context,
                                        context.getText(R.string.alert_dialog_note_deleted),
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                                onClickYes()
                                onDismissRequest(dialogState)
                            }) {
                            Text(
                                text = stringResource(id = R.string.alert_dialog_delete_button),
                                color = Color(0xFF1467C9),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                onDismissRequest(dialogState)
                            }) {
                            Text(
                                text = stringResource(id = R.string.alert_dialog_cancel_button),
                                color = Color(0xFF1467C9),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}
package com.elvitalya.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.elvitalya.notes.presentation.NavGraphs
import com.elvitalya.notes.ui.theme.NotesTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
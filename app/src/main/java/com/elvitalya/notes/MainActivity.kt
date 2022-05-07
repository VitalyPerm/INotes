package com.elvitalya.notes

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.elvitalya.notes.presentation.NavGraphs
import com.elvitalya.notes.ui.theme.NotesTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {

            val systemUiController = rememberSystemUiController()

            SideEffect {
                systemUiController.setSystemBarsColor(Color.Black, darkIcons = false)
            }

            NotesTheme {
                ProvideWindowInsets {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}
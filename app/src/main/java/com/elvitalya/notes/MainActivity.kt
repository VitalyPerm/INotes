package com.elvitalya.notes

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elvitalya.notes.presentation.notes_info.NoteInfoScreen
import com.elvitalya.notes.presentation.notes_list.NotesListScreen
import com.elvitalya.notes.ui.theme.NotesTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val systemUiController = rememberSystemUiController()

            SideEffect {
                systemUiController.setSystemBarsColor(Color.Black, darkIcons = false)
            }

            NotesTheme {
                ProvideWindowInsets {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        Main()
                    }
                }
            }
        }
    }
}


@Composable
fun Main() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.NotesList.route
    ) {
        composable(Screens.NotesList.route) {
            NotesListScreen(navController = navController)
        }
        composable(Screens.Details.route) {
            NoteInfoScreen(navController = navController)
        }
    }
}

sealed class Screens(val route: String) {
    object NotesList : Screens("LIST")
    object Details : Screens("DETAIL")
}
package com.elvitalya.notes.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elvitalya.notes.R
import com.elvitalya.notes.presentation.TopBar
import com.elvitalya.notes.presentation.debug
import com.elvitalya.notes.presentation.notes_info.NoteDetailsScreen
import com.elvitalya.notes.presentation.notes_list.NotesListScreen
import com.elvitalya.notes.presentation.pin_code.PinCodeScreen
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun Main(
    viewModel: MainViewModel = hiltViewModel(),
) {

    val navController = rememberNavController()
    var bottomBarVisibility by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.note_list_title))
        },
        floatingActionButton = {
            if (bottomBarVisibility) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(MainEvent.NewNote(navController))
                        bottomBarVisibility = false
                    },
                    shape = RoundedCornerShape(50),
                    backgroundColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            if (bottomBarVisibility) {
                BottomAppBar(
                    cutoutShape = RoundedCornerShape(50),
                    backgroundColor = Color.White,
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    listOf(MainScreen.All, MainScreen.Favorite).forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.title
                                )
                            },
                            alwaysShowLabel = false,
                            label = { Text(text = screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(0)
                                }
                            },
                            selectedContentColor = Color.Red,
                            unselectedContentColor = Color.Black
                        )
                    }
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .navigationBarsPadding()
    ) {
        NavHost(
            navController = navController,
            startDestination = if (debug) MainScreen.All.route else Destinations.PIN.route,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        ) {
            composable(MainScreen.All.route) {
                NotesListScreen(
                    notes = viewModel.state,
                    favorite = false,
                    onClickDelete = { note ->
                        viewModel.onEvent(
                            MainEvent.Delete(note = note)
                        )
                    },
                    onClickNote = { note ->
                        viewModel.onEvent(MainEvent.UpdateNote(navController, note.id))
                        bottomBarVisibility = false
                    },
                    onClickFavorite = { note ->
                        viewModel.onEvent(MainEvent.OnClickFavorite(note))
                    }
                )
            }
            composable(MainScreen.Favorite.route) {
                NotesListScreen(
                    notes = viewModel.state,
                    favorite = true,
                    onClickDelete = {
                        viewModel.onEvent(
                            MainEvent.Delete(note = it)
                        )
                    },
                    onClickNote = {
                        viewModel.onEvent(MainEvent.UpdateNote(navController, it.id))
                        bottomBarVisibility = false
                    },
                    onClickFavorite = {
                        viewModel.onEvent(MainEvent.OnClickFavorite(it))
                    }
                )
            }
            composable(
                "${Destinations.Details.route}/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                NoteDetailsScreen(
                    navController = navController,
                    noteId = id,
                    goBack = { bottomBarVisibility = true }
                )
            }
            composable(Destinations.PIN.route) {
                PinCodeScreen {
                    navController.navigate(MainScreen.All.route) {
                        popUpTo(0)
                        bottomBarVisibility = true
                    }
                }
            }
        }
    }
}

sealed class MainScreen(val route: String, val title: String, val icon: ImageVector) {
    object All : MainScreen("all", "Все", Icons.Filled.Home)
    object Favorite : MainScreen("favorite", "Важные", Icons.Filled.Favorite)
}


sealed class Destinations(val route: String) {
    object Details : Destinations("Detail")
    object PIN : Destinations("Pin")
}
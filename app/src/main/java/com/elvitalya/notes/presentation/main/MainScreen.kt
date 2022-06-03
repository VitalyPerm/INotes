package com.elvitalya.notes.presentation.main

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elvitalya.notes.R
import com.elvitalya.notes.presentation.TopBar
import com.elvitalya.notes.presentation.debug
import com.elvitalya.notes.presentation.notes_info.NoteDetailsScreen
import com.elvitalya.notes.presentation.notes_list.NotesListScreen
import com.elvitalya.notes.presentation.pin_code.PinCodeScreen
import com.elvitalya.notes.theme.theme.CardBackground
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun Main(
    viewModel: MainViewModel = hiltViewModel(),
) {

    var selectedItem by remember { mutableStateOf(Screens.NotesList.route) }
    val navController = rememberNavController()
    var bottomBarVisibility by remember { mutableStateOf(true) }

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
                    content = {
                        BottomNavigation {
                            BottomNavigationItem(
                                selected = selectedItem == Screens.NotesList.route,
                                onClick = {
                                    navController.navigate(Screens.NotesList.route) {
                                        popUpTo(0)
                                    }
                                    selectedItem = Screens.NotesList.route
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = Screens.NotesList.route) },
                                alwaysShowLabel = false,
                                modifier = Modifier
                                .background(Color.White),
                                selectedContentColor = Color.Red,
                                unselectedContentColor = Color.Black
                            )

                            BottomNavigationItem(
                                selected = selectedItem == Screens.NotesFavoriteList.route,
                                onClick = {
                                    navController.navigate(Screens.NotesFavoriteList.route) {
                                        popUpTo(0)
                                    }
                                    selectedItem = Screens.NotesFavoriteList.route
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = Screens.NotesFavoriteList.route) },
                                alwaysShowLabel = false,
                                modifier = Modifier
                                    .background(Color.White),
                                selectedContentColor = Color.Red,
                                unselectedContentColor = Color.Black
                            )
                        }
                    },
                )
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
            startDestination = if (debug) Screens.NotesList.route else Screens.PIN.route,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        ) {
            composable(Screens.NotesList.route) {
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
            composable(Screens.NotesFavoriteList.route) {
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
                "${Screens.Details.route}/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    })
            ) {
                val id = it.arguments?.getInt("id")
                NoteDetailsScreen(
                    navController = navController,
                    noteId = id,
                    goBack = { bottomBarVisibility = true }
                )
            }
            composable(Screens.PIN.route) {
                PinCodeScreen(navController = navController)
            }
        }
    }
}

sealed class Screens(val route: String) {
    object NotesList : Screens("Notes")
    object NotesFavoriteList : Screens("Favorite")
    object Details : Screens("DETAIL")
    object PIN : Screens("PIN")
}
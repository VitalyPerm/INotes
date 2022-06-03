package com.elvitalya.notes.presentation.main

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
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun Main(
    viewModel: NotesListViewModel = hiltViewModel(),
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
                    backgroundColor = Color.Yellow
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
                    content = {
                        BottomNavigation {
                            BottomNavigationItem(
                                selected = selectedItem == Screens.NotesList.route,
                                onClick = {
                                    navController.navigate(Screens.NotesList.route){
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
                                alwaysShowLabel = false
                            )

                            BottomNavigationItem(
                                selected = selectedItem == Screens.NotesFavoriteList.route,
                                onClick = {
                                    navController.navigate(Screens.NotesFavoriteList.route){
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
                                alwaysShowLabel = false
                            )
                        }
                    }
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
            startDestination = if (debug) Screens.NotesList.route else Screens.PIN.route
        ) {
            composable(Screens.NotesList.route) {
                NotesListScreen(
                    state = viewModel.state,
                    favorite = false,
                    onClickDelete = {
                        viewModel.onEvent(
                            MainEvent.Delete(note = it)
                        )
                    },
                    onClickNote = {
                        viewModel.onEvent(MainEvent.UpdateNote(navController, it.id))
                    }
                )
            }
            composable(Screens.NotesFavoriteList.route) {
                NotesListScreen(
                    state = viewModel.state,
                    favorite = true,
                    onClickDelete = {
                        viewModel.onEvent(
                            MainEvent.Delete(note = it)
                        )
                    },
                    onClickNote = {
                        viewModel.onEvent(MainEvent.UpdateNote(navController, it.id))
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
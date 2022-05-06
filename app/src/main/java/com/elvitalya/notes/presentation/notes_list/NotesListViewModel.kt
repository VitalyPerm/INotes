package com.elvitalya.notes.presentation.notes_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.notes.domain.repository.NotesRepository
import com.elvitalya.notes.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    var state by mutableStateOf(NotesListState())

    init {
        get()
    }

    fun get() {
        viewModelScope.launch {
            repository.getNotes()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(
                                    notes = it
                                )
                            }
                        }
                        else -> {

                        }
                    }
                }
        }
    }
}
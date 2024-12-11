package com.lmorda.explore.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmorda.domain.DataRepository
import com.lmorda.explore.repos.DetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val savedIdKey = "id"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DataRepository
) : ViewModel() {

    private val id: Long? = savedStateHandle[savedIdKey]

    private val _state = MutableStateFlow(DetailsUiState())
    val state: StateFlow<DetailsUiState> = _state.asStateFlow()

    init {
        id?.let { repoId ->
            viewModelScope.launch {
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(
                    isLoading = false,
                    githubRepo = repository.getRepo(
                        id = repoId,
                    ),
                )
            }
        }
    }
}

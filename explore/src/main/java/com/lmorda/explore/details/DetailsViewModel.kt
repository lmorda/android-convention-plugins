package com.lmorda.explore.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmorda.domain.DataRepository
import com.lmorda.domain.util.allNotNull
import com.lmorda.explore.repos.DetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val savedIdKey = "id"
const val savedOwnerNameKey = "ownerName"
const val savedRepoNameKey = "repoName"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DataRepository
) : ViewModel() {

    private val id: Long? = savedStateHandle[savedIdKey]
    private val ownerName: String? = savedStateHandle[savedOwnerNameKey]
    private val repoName: String? = savedStateHandle[savedRepoNameKey]

    private val _state = MutableStateFlow(DetailsUiState())
    val state: StateFlow<DetailsUiState> = _state.asStateFlow()

    init {
        allNotNull(id, ownerName, repoName) { id, ownerName, repoName ->
            viewModelScope.launch {
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(
                    isLoading = false,
                    githubRepo = repository.getRepo(
                        id = id,
                        owner = ownerName,
                        name = repoName,
                    ),
                )
            }
        }
    }
}

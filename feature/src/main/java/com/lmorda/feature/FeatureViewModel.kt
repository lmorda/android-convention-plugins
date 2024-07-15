package com.lmorda.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmorda.domain.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FeatureUiState())
    val state: StateFlow<FeatureUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            _state.value = state.value.copy(isLoading = false, githubRepos = repository.getRepos())
        }
    }
}

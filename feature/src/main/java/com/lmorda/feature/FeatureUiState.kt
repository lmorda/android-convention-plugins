package com.lmorda.feature

import com.lmorda.domain.model.GithubRepo

data class FeatureUiState(
    val isLoading: Boolean = false,
    val githubRepos: List<GithubRepo> = emptyList(),
)

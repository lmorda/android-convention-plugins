package com.lmorda.feature

import com.lmorda.domain.GithubRepo

data class FeatureUiState(
    val isLoading: Boolean = false,
    val githubRepos: List<GithubRepo> = emptyList(),
)

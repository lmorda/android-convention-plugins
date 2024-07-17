package com.lmorda.explore

import com.lmorda.domain.model.GithubRepo

data class ExploreUiState(
    val isLoading: Boolean = false,
    val githubRepos: List<GithubRepo> = emptyList(),
)

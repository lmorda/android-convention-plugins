package com.lmorda.explore.repos

import com.lmorda.domain.model.GithubRepo

data class ExploreUiState(
    val isLoading: Boolean = false,
    val githubRepos: List<GithubRepo> = emptyList(),
)

data class DetailsUiState(
    val isLoading: Boolean = false,
    val githubRepo: GithubRepo? = null,
)

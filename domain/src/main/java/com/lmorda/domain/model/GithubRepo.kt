package com.lmorda.domain.model

data class GithubRepo(
    val id: Long,
    val name: String,
    val owner: Owner,
    val description: String?,
    val stargazersCount: Int?,
    val forksCount: Int?,
    val language: String?,
)

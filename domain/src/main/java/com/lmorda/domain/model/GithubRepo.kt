package com.lmorda.domain.model

data class GithubRepo(
    val id: Long,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val description: String,
    val stargazersCount: Int,
    val language: String,
    val forksCount: Int,
)

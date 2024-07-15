package com.lmorda.domain

interface DataRepository {

    suspend fun getRepos(): List<GithubRepo>

}

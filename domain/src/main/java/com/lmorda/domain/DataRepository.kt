package com.lmorda.domain

import com.lmorda.domain.model.GithubRepo

interface DataRepository {

    suspend fun getRepos(): List<GithubRepo>

}

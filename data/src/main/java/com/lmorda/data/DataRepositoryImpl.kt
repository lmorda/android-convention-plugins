package com.lmorda.data

import com.lmorda.data.mapper.GithubRepoMapper
import com.lmorda.domain.DataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepositoryImpl @Inject constructor(
    private val githubApiService: ApiService,
) : DataRepository {

    private val githubRepoMapper = GithubRepoMapper()

    override suspend fun getRepos() =
        githubApiService.getRepos("lmorda").map(githubRepoMapper::map)

}

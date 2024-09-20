package com.lmorda.data

import com.lmorda.data.mapper.GithubRepoMapper
import com.lmorda.domain.DataRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepositoryImpl @Inject constructor(
    private val githubApiService: ApiService,
    private val githubRepoMapper: GithubRepoMapper,
) : DataRepository {

    override suspend fun getRepos() =
        try {
            val repos = githubApiService.getMostStarredGoogleRepos()
            githubRepoMapper.map(githubRepoItemsDto = repos)
        } catch (ex: Exception) {
            Timber.e(ex)
            emptyList()
        }

    override suspend fun getRepo(id: Long) =
        try {
            val repo = githubApiService.getRepo(id = id)
            githubRepoMapper.map(githubRepoDto = repo)
        } catch (ex: Exception) {
            Timber.e(ex)
            null
        }
}

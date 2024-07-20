package com.lmorda.data

import com.lmorda.data.mapper.GithubRepoMapper
import com.lmorda.domain.DataRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepositoryImpl @Inject constructor(
    private val githubApiService: ApiService,
) : DataRepository {

    private val githubRepoMapper = GithubRepoMapper()

    override suspend fun getRepos() =
        try {
            githubRepoMapper.map(
                githubApiService
                    .getMostStarredGoogleRepos()
            )
        } catch (ex: Exception) {
            Timber.e(ex)
            emptyList()
        }
}

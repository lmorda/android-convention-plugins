package com.lmorda.data

import com.lmorda.data.model.GithubRepoDto
import com.lmorda.data.model.GithubRepoItemsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/repositories")
    suspend fun getMostStarredGoogleRepos(
        @Query("q") query: String = "google",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): GithubRepoItemsDto

    @GET("repositories/{id}")
    suspend fun getRepo(
        @Path("id") id: Long,
    ): GithubRepoDto
}

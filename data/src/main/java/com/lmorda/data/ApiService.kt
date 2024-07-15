package com.lmorda.data

import com.lmorda.data.model.GithubRepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String): List<GithubRepoDto>
}
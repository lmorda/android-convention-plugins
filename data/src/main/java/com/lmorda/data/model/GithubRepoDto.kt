package com.lmorda.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepoDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("full_name") val fullName: String?,
    @SerialName("owner") val owner: OwnerDto?,
    @SerialName("description") val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int?,
    @SerialName("language") val language: String?,
    @SerialName("forks_count") val forksCount: Int?,
)

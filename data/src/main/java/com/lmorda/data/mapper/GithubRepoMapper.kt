package com.lmorda.data.mapper

import com.lmorda.data.model.GithubRepoItemsDto
import com.lmorda.domain.model.GithubRepo
import com.lmorda.domain.model.Owner
import javax.inject.Inject

class GithubRepoMapper @Inject constructor() {

    fun map(githubRepoItemsDto: GithubRepoItemsDto) =
        githubRepoItemsDto.items.map {
            with(it) {
                GithubRepo(
                    id = id,
                    name = name ?: "",
                    fullName = fullName ?: "",
                    owner = Owner(
                        login = owner?.login ?: "",
                        avatarUrl = owner?.avatarUrl ?: "",
                    ),
                    description = description ?: "",
                    stargazersCount = stargazersCount ?: 0,
                    language = language ?: "",
                    forksCount = forksCount ?: 0,
                )
            }
        }
}

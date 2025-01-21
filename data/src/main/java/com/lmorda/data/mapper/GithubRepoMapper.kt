package com.lmorda.data.mapper

import com.lmorda.data.model.GithubRepoDto
import com.lmorda.data.model.GithubRepoItemsDto
import com.lmorda.domain.model.GithubRepo
import com.lmorda.domain.model.Owner
import javax.inject.Inject

class GithubRepoMapper @Inject constructor() {

    fun map(githubRepoItemsDto: GithubRepoItemsDto) =
        githubRepoItemsDto.items.map {
            map(githubRepoDto = it)
        }

    fun map(githubRepoDto: GithubRepoDto) =
        with(githubRepoDto) {
            GithubRepo(
                id = id,
                name = name,
                owner = Owner(
                    login = owner.login,
                    avatarUrl = owner.avatarUrl,
                ),
                description = description,
                stargazersCount = stargazersCount,
                language = language,
                forksCount = forksCount,
            )
        }
}

package com.lmorda.data.mapper

import com.lmorda.data.model.GithubRepoDto
import com.lmorda.domain.model.GithubRepo
import com.lmorda.domain.model.Owner

class GithubRepoMapper {

    fun map(githubRepoDto: GithubRepoDto) =
        with(githubRepoDto) {
            GithubRepo(
                id = id,
                name = name,
                fullName = fullName,
                owner = Owner(owner.login, owner.avatarUrl),
                description = description,
                stargazersCount = stargazersCount,
                language = language,
                forksCount = forksCount,
            )
        }
}

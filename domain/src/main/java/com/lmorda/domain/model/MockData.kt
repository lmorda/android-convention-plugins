package com.lmorda.domain.model

val mockDomainData = listOf(
    GithubRepo(
        id = 0,
        name = "my-application",
        fullName = "My Application",
        owner = Owner("lmorda", ""),
        description = "A quick and dirty sample of some fun Kotlin DSL modularization stuff",
        stargazersCount = 345123,
        language = "kotlin",
        forksCount = 99,
    ),
    GithubRepo(
        id = 1,
        name = "my-application-2",
        fullName = "My Application 2",
        owner = Owner("lmorda", ""),
        description = "This is just some garbage test data",
        stargazersCount = 345345,
        language = "kotlin",
        forksCount = 234,
    ),
)

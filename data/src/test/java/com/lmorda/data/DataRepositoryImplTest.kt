package com.lmorda.data

import com.lmorda.data.mapper.GithubRepoMapper
import com.lmorda.data.model.mockApiData
import com.lmorda.domain.model.mockDomainData
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DataRepositoryImplTest {

    private val mockApiService = mockk<ApiService>()
    private val githubRepoMapper = mockk<GithubRepoMapper> {
        every { map(githubRepoItemsDto = mockApiData) } returns mockDomainData
        every { map(githubRepoDto = mockApiData.items[0]) } returns mockDomainData[0]
    }
    private val dataRepository = DataRepositoryImpl(
        githubApiService = mockApiService,
        githubRepoMapper = githubRepoMapper,
    )

    @Test
    fun `getRepos should return mapped repos`() = runTest {
        coEvery { mockApiService.getMostStarredGoogleRepos() } returns mockApiData
        val repos = dataRepository.getRepos()
        assertEquals(mockDomainData, repos)
    }

    @Test
    fun `getRepo should return mapped repo`() = runTest {
        coEvery { mockApiService.getRepo(id = 0) } returns mockApiData.items[0]
        val repos = dataRepository.getRepo(id = 0)
        assertEquals(mockDomainData[0], repos)
    }
}

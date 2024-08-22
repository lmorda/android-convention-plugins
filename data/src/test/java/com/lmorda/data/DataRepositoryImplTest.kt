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
}

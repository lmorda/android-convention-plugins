package com.lmorda.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.lmorda.domain.DataRepository
import com.lmorda.domain.model.mockDomainData
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeatureViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val repository: DataRepository = mockk()
    private lateinit var viewModel: FeatureViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun viewModelTest() = runTest {
        coEvery { repository.getRepos() } returns mockDomainData
        viewModel = FeatureViewModel(repository)
        viewModel.state.test {
            assertEquals(FeatureUiState(isLoading = false), awaitItem())
            assertEquals(FeatureUiState(isLoading = true), awaitItem())
            assertEquals(FeatureUiState(isLoading = false, githubRepos = mockDomainData), awaitItem())
        }
    }
}

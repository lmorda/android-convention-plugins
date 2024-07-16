package com.lmorda.feature

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.lmorda.domain.model.mockDomainData
import org.junit.Rule
import org.junit.Test

class FeatureScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun featureScreenTest() {
        composeTestRule.setContent {
            FeatureScreen(
                state = FeatureUiState(
                    isLoading = false,
                    githubRepos = mockDomainData,
                ),
            )
        }
        composeTestRule.onNodeWithText("my-application").assertIsDisplayed()
    }
}

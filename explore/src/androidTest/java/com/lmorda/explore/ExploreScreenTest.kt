package com.lmorda.explore

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.lmorda.domain.model.mockDomainData
import org.junit.Rule
import org.junit.Test

class ExploreScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun featureScreenTest() {
        composeTestRule.setContent {
            ExploreScreen(
                state = ExploreUiState(
                    isLoading = false,
                    githubRepos = mockDomainData,
                ),
            )
        }

        composeTestRule.onNodeWithText("my-application").assertIsDisplayed()
        composeTestRule.onNodeWithText("description for google my application").assertIsDisplayed()

        composeTestRule.onNodeWithText("my-application-2").assertIsDisplayed()
        composeTestRule.onNodeWithText("description for google my application 2").assertIsDisplayed()
    }
}

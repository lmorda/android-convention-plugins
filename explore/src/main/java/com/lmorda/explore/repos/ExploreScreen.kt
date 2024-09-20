package com.lmorda.explore.repos

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lmorda.domain.model.GithubRepo
import com.lmorda.domain.model.mockDomainData
import com.lmorda.design.theme.ConventionTheme
import com.lmorda.design.theme.marginDefault
import com.lmorda.design.theme.marginMedium
import com.lmorda.design.theme.marginXLarge
import com.lmorda.explore.R
import com.lmorda.explore.RepoItemTitle

@Composable
fun ExploreScreen(
    state: ExploreUiState,
    onNavigateToDetails: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Text(
                modifier = Modifier.padding(all = marginDefault),
                text = stringResource(id = R.string.explore_title),
                style = MaterialTheme.typography.titleMedium,
            )
        }
        HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp,
        )
        when {
            state.isLoading -> ExploreProgressIndicator()
            else -> LazyColumn {
                items(state.githubRepos) { details ->
                    ExploreItem(details = details, onNavigateToDetails = onNavigateToDetails)
                }
            }
        }
    }
}

@Composable
private fun ExploreProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(width = marginXLarge),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun ExploreItem(details: GithubRepo, onNavigateToDetails: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .padding(all = marginMedium)
            .clickable {
                onNavigateToDetails(details.id)
            }
    ) {
        RepoItemTitle(details)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExploreScreenPreview() {
    ConventionTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            ExploreScreen(
                state = ExploreUiState(
                    isLoading = false,
                    githubRepos = mockDomainData,
                ),
                onNavigateToDetails = {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExploreScreenPreviewLoading() {
    ConventionTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            ExploreScreen(
                state = ExploreUiState(
                    isLoading = true,
                    githubRepos = emptyList(),
                ),
                onNavigateToDetails = {},
            )
        }
    }
}

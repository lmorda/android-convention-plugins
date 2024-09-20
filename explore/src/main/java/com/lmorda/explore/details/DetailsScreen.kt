package com.lmorda.explore.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lmorda.design.theme.ConventionTheme
import com.lmorda.design.theme.Green80
import com.lmorda.design.theme.Grey80
import com.lmorda.design.theme.Yellow80
import com.lmorda.design.theme.marginDefault
import com.lmorda.design.theme.marginMedium
import com.lmorda.domain.model.mockDomainData
import com.lmorda.explore.repos.DetailsUiState
import com.lmorda.explore.R
import com.lmorda.explore.RepoItemTitle

@Composable
fun DetailsScreen(
    state: DetailsUiState,
    onNavigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = marginDefault, vertical = marginMedium)
            .fillMaxSize()
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = Grey80,
                contentDescription = "back",
            )
        }
        state.githubRepo?.let { details ->
            RepoItemTitle(details)
            DetailsDescription(details.description)
            DetailsStargazers(details.stargazersCount)
            DetailsForks(details.forksCount)
        }
    }
}

@Composable
private fun DetailsDescription(description: String) {
    if (description.isNotBlank()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun DetailsForks(count: Int) {
    Row(
        modifier = Modifier.padding(top = 2.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Build,
            tint = Green80,
            contentDescription = "build",
        )
        Text(
            modifier = Modifier
                .padding(start = marginMedium)
                .align(Alignment.CenterVertically),
            text = stringResource(
                R.string.forks_under_construction,
                countPrettyString(count),
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun DetailsStargazers(count: Int) {
    Row(
        modifier = Modifier.padding(top = marginDefault),
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            tint = Yellow80,
            contentDescription = "star",
        )
        Text(
            modifier = Modifier
                .padding(start = marginMedium)
                .align(Alignment.CenterVertically),
            text = stringResource(
                id = R.string.stargazers,
                countPrettyString(count),
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

private fun countPrettyString(value: Int): String {
    return when {
        value >= 1_000_000 -> "${"%.1f".format(value / 1_000_000.0)}M"
        value >= 1_000 -> "${"%.1f".format(value / 1_000.0)}k"
        else -> value.toString()
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsScreenPreview() {
    ConventionTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            DetailsScreen(
                state = DetailsUiState(
                    isLoading = false,
                    githubRepo = mockDomainData[0],
                ),
                onNavigateBack = {},
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsScreenPreviewLoading() {
    ConventionTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            DetailsScreen(
                state = DetailsUiState(
                    isLoading = true,
                    githubRepo = null,
                ),
                onNavigateBack = {},
            )
        }
    }
}

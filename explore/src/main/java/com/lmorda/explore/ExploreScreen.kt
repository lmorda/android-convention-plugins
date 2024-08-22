package com.lmorda.explore

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lmorda.domain.model.GithubRepo
import com.lmorda.domain.model.mockDomainData
import com.lmorda.design.theme.ConventionTheme
import com.lmorda.design.theme.Green80
import com.lmorda.design.theme.Yellow80
import com.lmorda.design.theme.marginDefault
import com.lmorda.design.theme.marginMedium
import com.lmorda.design.theme.marginXLarge

@Composable
fun ExploreScreen(
    state: ExploreUiState,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Row {
            Text(
                modifier = Modifier.padding(all = marginDefault),
                text = stringResource(id = R.string.explore_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        ExploreDivider()
        when {
            state.isLoading -> ExploreProgressIndicator()
            else -> ExploreItems(state)
        }
    }
}

@Composable
private fun ExploreDivider() {
    HorizontalDivider(
        modifier = Modifier.shadow(
            elevation = 1.dp,
            spotColor = MaterialTheme.colorScheme.onBackground,
        ),
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 1.dp,
    )
}

@Composable
private fun ExploreItems(state: ExploreUiState) {
    LazyColumn {
        items(state.githubRepos) { details ->
            ExploreListItem(details = details)
            ExploreDivider()
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
fun ExploreListItem(details: GithubRepo) {
    Column(
        modifier = Modifier
            .padding(all = marginDefault)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        ExploreItemTitle(details)
        ExploreItemDescription(details.description)
        ExploreItemStargazers(details.stargazersCount)
        ExploreItemForks(details.forksCount)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ExploreItemTitle(details: GithubRepo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = marginMedium)
    ) {
        details.owner.avatarUrl.takeIf { it.isNotBlank() }?.let {
            GlideImage(
                modifier = Modifier
                    .size(size = 40.dp)
                    .clip(shape = CircleShape),
                model = details.owner.avatarUrl,
                contentDescription = details.owner.avatarUrl,
            )
        } ?: Image(
            modifier = Modifier.size(size = 40.dp),
            painter = painterResource(id = R.drawable.ic_android_green_24dp),
            contentDescription = null,
        )
        Column(modifier = Modifier.padding(start = marginDefault)) {
            Text(
                text = details.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = details.owner.login,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun ExploreItemDescription(description: String) {
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
private fun ExploreItemForks(count: Int) {
    Row(
        modifier = Modifier.padding(top = 2.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Build,
            contentDescription = null,
            tint = Green80,
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
private fun ExploreItemStargazers(count: Int) {
    Row(
        modifier = Modifier.padding(top = marginDefault),
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            tint = Yellow80,
            contentDescription = null,
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
fun ExploreScreenPreview() {
    ConventionTheme {
        ExploreScreen(
            state = ExploreUiState(
                isLoading = false,
                githubRepos = mockDomainData,
            ),
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExploreScreenPreviewLoading() {
    ConventionTheme {
        ExploreScreen(
            state = ExploreUiState(
                isLoading = true,
                githubRepos = emptyList(),
            ),
        )
    }
}

package com.lmorda.explore

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lmorda.domain.model.GithubRepo
import com.lmorda.domain.model.mockDomainData
import com.lmorda.design.theme.ConventionTheme

@Composable
fun ExploreScreen(
    state: ExploreUiState,
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Row {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = "Google Github Repositories",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
        HorizontalDivider(
            modifier = Modifier.shadow(2.dp),
            color = Color.Gray.copy(alpha = 0.5f),
            thickness = 1.dp,
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        } else {
            LazyColumn {
                items(state.githubRepos) { details ->
                    ExploreListItem(details = details)
                    HorizontalDivider(
                        modifier = Modifier.shadow(2.dp),
                        color = Color.Gray.copy(alpha = 0.5f),
                        thickness = 1.dp,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExploreListItem(details: GithubRepo) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            val imageSize = 40.dp
            details.owner.avatarUrl.takeIf { it.isNotBlank() }?.let {
                GlideImage(
                    modifier = Modifier
                        .size(size = imageSize)
                        .clip(shape = CircleShape),
                    model = details.owner.avatarUrl,
                    contentDescription = details.owner.avatarUrl,
                )
            } ?: Image(
                modifier = Modifier.size(size = imageSize),
                painter = painterResource(id = R.drawable.ic_android_black_24dp),
                contentDescription = null,
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = details.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = details.owner.login,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (details.description.isNotBlank()) {
            Text(
                text = details.description,
                fontSize = 16.sp,
                color = Color.DarkGray,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Row(
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFD700),
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "${formatNumber(details.stargazersCount)} Stargazers",
                fontSize = 16.sp,
                color = Color.DarkGray,
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Build,
                contentDescription = null,
                tint = Color(0xFF3DDC84),
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "${formatNumber(details.forksCount)} Forks under construction",
                fontSize = 16.sp,
                color = Color.DarkGray,
            )
        }
    }
}

private fun formatNumber(value: Int): String {
    return when {
        value >= 1_000_000 -> "${"%.1f".format(value / 1_000_000.0)}M"
        value >= 1_000 -> "${"%.1f".format(value / 1_000.0)}k"
        else -> value.toString()
    }
}

@Preview
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


@Preview
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


package com.lmorda.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lmorda.domain.GithubRepo
import com.lmorda.domain.testListData

@Composable
fun FeatureScreen(
    state: FeatureUiState,
) {
    // TODO: Hookup isLoading
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 164.dp)
    ) {
        items(state.githubRepos) { details ->
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .width(120.dp)
                    .padding(8.dp)
            ) {
                SampleGridItem(
                    details = details
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SampleGridItem(details: GithubRepo) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            GlideImage(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                model = details.owner.avatarUrl,
                contentDescription = details.owner.avatarUrl,
            )
            Text(
                modifier = Modifier.align(CenterVertically),
                text = details.owner.login,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = details.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = details.description,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun FeatureScreenPreview() {
    FeatureScreen(state = FeatureUiState(isLoading = false, githubRepos = testListData))
}

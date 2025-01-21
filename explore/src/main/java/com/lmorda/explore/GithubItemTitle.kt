package com.lmorda.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lmorda.design.theme.marginDefault
import com.lmorda.design.theme.marginMedium
import com.lmorda.domain.model.GithubRepo

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
internal fun RepoItemTitle(details: GithubRepo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = marginMedium)
    ) {
        GlideImage(
            modifier = Modifier
                .size(size = 40.dp)
                .clip(shape = CircleShape),
            model = details.owner.avatarUrl,
            contentDescription = "avatar",
        )
        Column(modifier = Modifier.padding(start = marginDefault)) {
            Text(
                text = details.owner.login,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = details.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

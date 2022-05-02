package com.gumbachi.ezra.ui.screens.edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun EpisodeProgressAdjuster(
    episodesWatched: Int,
    totalEpisodes: Int,
    onProgressAdd: () -> Unit = {},
    onProgressSubtract: () -> Unit = {}
) {
    EditModule(title = "Series Progress") {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            // Subtract Button
            ProgressButton(icon = Icons.Default.Remove) {
                onProgressSubtract()
            }

            Text(
                text = "${episodesWatched}/${totalEpisodes}",
                fontSize = 20.sp
            )

            // Add Button
            ProgressButton(icon = Icons.Default.Add) {
                onProgressAdd()
            }
        }
    }
}

@Composable
fun ProgressButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconColor: Color = MaterialTheme.colors.onPrimary,
    iconBackgroundColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
            .clip(CircleShape)
            .background(iconBackgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor
        )
    }
}

@Preview
@Composable
fun PreviewEpisodeProgressAdjuster() {
    EpisodeProgressAdjuster(episodesWatched = 12, totalEpisodes = 24)
}
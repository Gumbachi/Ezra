package com.gumbachi.ezra.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gumbachi.ezra.R
import com.gumbachi.ezra.data.dummy.DummyData
import com.gumbachi.ezra.model.Media
import com.gumbachi.ezra.model.Movie
import com.gumbachi.ezra.ui.theme.EzraTheme
import com.gumbachi.ezra.model.Anime
import com.gumbachi.ezra.model.Show
import com.gumbachi.ezra.utils.getCompleteTMDBPath

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaCard(
    imageURL: String,
    title: String,
    primarySubtitle: String,
    score: Number,
    modifier: Modifier = Modifier,
    secondarySubtitle: String? = null,
    fallbackImage: Painter? = null,
    totalEpisodes: Int = -1,
    episodesWatched: Int = 0,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = 20.dp,
    ) {
        Column {
            Box(modifier = modifier) {
                AsyncImage(
                    model = imageURL,
                    placeholder = fallbackImage ?: painterResource(R.drawable.image_not_found),
                    error = fallbackImage ?: painterResource(R.drawable.image_not_found),
                    contentDescription = "$title Poster",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )
                ScoreCircle(
                    score = score,
                    modifier = Modifier
                        .fillMaxWidth(0.2F)
                        .fillMaxHeight(0.1F)
                        .align(Alignment.BottomEnd)
                )

                // Draw Progress Bar if not movie/single episode
                if (totalEpisodes > 0) {
                    EpisodeProgressCircle(
                        totalEpisodes = totalEpisodes,
                        episodesWatched = episodesWatched,
                        modifier = Modifier
                            .fillMaxWidth(0.4F)
                            .fillMaxHeight(0.1F)
                            .align(Alignment.BottomStart)
                    )
                }
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(start = 8.dp, end = 8.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = primarySubtitle,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // optional third text line
                secondarySubtitle?.let {
                    Text(
                        text = it,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


@Composable
fun ScoreCircle(
    score: Number,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape = RoundedCornerShape(topStart = 10.dp))
            .background(color = Color.Black.copy(alpha = 0.6f))

    ) {
        Text(
            fontSize = 15.sp,
            text = score.toInt().toString(),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(2.dp)
        )
    }
}

@Composable
fun EpisodeProgressCircle(
    totalEpisodes: Int,
    episodesWatched: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape = RoundedCornerShape(topEnd = 10.dp))
            .background(color = Color.Black.copy(alpha = 0.6f))

    ) {
        Text(
            fontSize = 15.sp,
            text = "$episodesWatched/$totalEpisodes",
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(2.dp)
        )
    }
}


@Preview(showBackground = true, heightDp = 360, widthDp = 240)
@Composable
fun PreviewMediaCard() {
    EzraTheme(darkTheme = true) {
        Surface {
            MediaCard(
                imageURL = "",
                title = "Example Show",
                primarySubtitle = "7 Seasons",
                secondarySubtitle = "9-7-2000",
                score = 10,
            )
        }

    }
}
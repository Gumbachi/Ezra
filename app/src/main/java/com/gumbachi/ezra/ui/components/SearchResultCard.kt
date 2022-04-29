package com.gumbachi.ezra.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import com.gumbachi.ezra.data.remote.responses.tv.TVSearchResult
import com.gumbachi.ezra.data.remote.responses.movie.MovieSearchResult
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gumbachi.ezra.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieSearchResultCard(
    searchResult: MovieSearchResult,
    onClickAction: () -> Unit,
    onLongClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .combinedClickable(
                onClick = { onClickAction() },
                onLongClick = { onLongClickAction() }
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = 20.dp
    ) {
        Column {

            SearchCardTop(
                imageURL = "https://image.tmdb.org/t/p/w300${searchResult.poster_path}",
                score = searchResult.vote_average.toInt(),
                modifier = Modifier.fillMaxHeight(0.8F)
            )
            SearchCardBottom(
                headline = searchResult.title,
                subline = searchResult.release_date
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TVSearchResultCard(
    searchResult: TVSearchResult,
    onClickAction: () -> Unit,
    onLongClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .combinedClickable(
                onClick = { onClickAction() },
                onLongClick = { onLongClickAction() }
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = 20.dp
    ) {
        Column {

            SearchCardTop(
                imageURL = "https://image.tmdb.org/t/p/w300${searchResult.poster_path}",
                score = searchResult.vote_average.toInt(),
                modifier = Modifier.fillMaxHeight(0.8F)
            )
            SearchCardBottom(
                headline = searchResult.name,
                subline = searchResult.first_air_date
            )
        }
    }
}


@Composable
fun SearchCardTop(
    imageURL: String,
    score: Int,
    modifier: Modifier = Modifier
) {
    // The Image and Associated Circles
    Box(modifier = modifier) {
        AsyncImage(
            model = imageURL,
            placeholder = painterResource(R.drawable.image_not_found),
            error = painterResource(R.drawable.image_not_found),
            contentDescription = null,
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
    }
}

@Composable
fun SearchCardBottom(
    headline: String,
    subline: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = headline,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subline,
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewMediaCardBottom() {
//    EzraTheme(darkTheme = true) {
//        Surface {
//            Column(modifier = Modifier.fillMaxSize()) {
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(0.8F)
//                        .background(Color.Red)
//                )
//                MediaCardBottom(
//                    media = DummyData.show
//                )
//            }
//        }
//    }
//}

//@Preview(showBackground = true, heightDp = 360, widthDp = 240)
//@Composable
//fun PreviewSearchResultCard() {
//    EzraTheme(darkTheme = true) {
//        Surface {
//            SearchResult(media = DummyData.show)
//        }
//
//    }
//}
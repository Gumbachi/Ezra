package com.gumbachi.ezra.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gumbachi.ezra.SearchAnimeQuery
import com.gumbachi.ezra.data.remote.responses.tv.TVSearchResult
import com.gumbachi.ezra.data.remote.responses.movie.MovieSearchResult
import com.gumbachi.ezra.model.Anime
import com.gumbachi.ezra.repository.EditRepository
import com.gumbachi.ezra.model.Movie
import com.gumbachi.ezra.model.Show
import com.gumbachi.ezra.ui.theme.EzraTheme
import com.gumbachi.ezra.utils.getCompleteTMDBPath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaCardList(
    modifier: Modifier = Modifier,
    span: Int = 2,
    content: LazyGridScope.() -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxHeight(),
        columns = GridCells.Fixed(span),
        contentPadding = PaddingValues(top = 4.dp, start = 2.dp, end = 2.dp),
        content = content
    )
}

@Composable
fun AnimeSearchResultList(
    navController: NavHostController,
    scope: CoroutineScope,
    list: List<SearchAnimeQuery.Medium>,
    modifier: Modifier = Modifier,
    span: Int = 2,
    onItemClicked: () -> Unit = {}
) {
    MediaCardList(span = span) {
        itemsIndexed(list) { _, item ->
            MediaCard(
                imageURL = item.coverImage?.large ?: "",
                title = item.title?.english ?: item.title?.english ?: item.title?.native ?: "Missing Title",
                primarySubtitle = "${item.episodes ?: 0} Episodes",
                secondarySubtitle = "${item.status}",
                score = ((item.averageScore ?: 0) + 5) / 10,
                onClick = {
                    onItemClicked()
                    navController.navigate("details/ANIME/${item.id}")
                },
                onLongClick = {
                    onItemClicked()
                    scope.launch {
                        EditRepository.generateEditable(item)
                        navController.navigate("edit")
                    }
                },
            )
        }
    }
}

@Composable
fun MovieSearchResultList(
    navController: NavHostController,
    scope: CoroutineScope,
    list: List<MovieSearchResult>,
    modifier: Modifier = Modifier,
    span: Int = 2,
    onItemClicked: () -> Unit = {}
) {
    MediaCardList(span = span) {
        itemsIndexed(list) { _, item ->
            MediaCard(
                modifier = modifier,
                imageURL = getCompleteTMDBPath(item.poster_path),
                title = item.title,
                primarySubtitle = item.release_date,
                score = item.vote_average,
                onClick = {
                    onItemClicked()
                    navController.navigate("details/MOVIES/${item.id}")
                },
                onLongClick = {
                    onItemClicked()
                    scope.launch {
                        EditRepository.generateEditable(item)
                        navController.navigate("edit")
                    }
                },
            )
        }
    }
}

@Composable
fun TVSearchResultList(
    navController: NavHostController,
    scope: CoroutineScope,
    list: List<TVSearchResult>,
    modifier: Modifier = Modifier,
    span: Int = 2,
    onItemClicked: () -> Unit = {}
) {
    MediaCardList(span = span) {
        itemsIndexed(list) { _, item ->
            MediaCard(
                modifier = modifier,
                imageURL = getCompleteTMDBPath(item.poster_path),
                title = item.name,
                primarySubtitle = item.first_air_date,
                score = item.vote_average,
                onClick = {
                    onItemClicked()
                    navController.navigate("details/TV/${item.id}")
                },
                onLongClick = {
                    onItemClicked()
                    scope.launch {
                        EditRepository.generateEditable(item)
                        navController.navigate("edit")
                    }
                },
            )
        }
    }
}

@Composable
fun MovieList(
    navController: NavHostController,
    list: List<Movie>,
    modifier: Modifier = Modifier,
    span: Int = 2
) {
    MediaCardList(span = span) {
        itemsIndexed(list) { _, item ->
            MediaCard(
                modifier = modifier,
                imageURL = item.imageURL,
                title = item.title,
                primarySubtitle = item.releaseDate,
                secondarySubtitle = item.runtime,
                score = item.score,
                onClick = {
                    navController.navigate("details/MOVIES/${item.id}")
                },
                onLongClick = {
                    EditRepository.underEdit = item
                    navController.navigate("edit")
                },
            )
        }
    }
}

@Composable
fun TVList(
    navController: NavHostController,
    list: List<Show>,
    modifier: Modifier = Modifier,
    span: Int = 2
) {
    MediaCardList(span = span) {
        itemsIndexed(list) { _, item ->
            MediaCard(
                modifier = modifier,
                imageURL = item.imageURL,
                title = item.title,
                primarySubtitle = item.releaseDate,
                secondarySubtitle = "${item.numSeasons} ${if (item.numSeasons == 1) "Season" else "Seasons"}",
                score = item.score,
                episodesWatched = item.episodesWatched,
                totalEpisodes = item.totalEpisodes,
                onClick = {
                    navController.navigate("details/TV/${item.id}")
                },
                onLongClick = {
                    EditRepository.underEdit = item
                    navController.navigate("edit")
                },
            )
        }
    }
}

@Composable
fun AnimeList(
    navController: NavHostController,
    list: List<Anime>,
    modifier: Modifier = Modifier,
    span: Int = 2
) {
    MediaCardList(span = span) {
        itemsIndexed(list) { _, item ->
            MediaCard(
                modifier = modifier,
                imageURL = item.imageURL,
                title = item.title,
                primarySubtitle = item.releaseDate,
                secondarySubtitle = "${item.totalEpisodes} Episodes",
                score = item.score,
                episodesWatched = item.episodesWatched,
                totalEpisodes = item.totalEpisodes,
                onClick = {
                    navController.navigate("details/ANIME/${item.id}")
                },
                onLongClick = {
                    EditRepository.underEdit = item
                    navController.navigate("edit")
                },
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMediaList() {
    EzraTheme(darkTheme = true) {
        Surface {
            TVList(
                navController = rememberNavController(),
                list = Show.getDummies(5)
            )
        }
    }
}
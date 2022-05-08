package com.gumbachi.ezra.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.gumbachi.ezra.repository.MovieRepository
import com.gumbachi.ezra.model.WatchStatus
import com.gumbachi.ezra.ui.components.MediaCardList
import com.gumbachi.ezra.ui.components.MovieList
import com.gumbachi.ezra.ui.components.WatchStatusTabRow
import com.gumbachi.ezra.ui.theme.EzraTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoviesScreen(
    navController: NavHostController,
    scope: CoroutineScope,
    startingTab: WatchStatus = WatchStatus.WATCHING
) {
    var currentTab by rememberSaveable { mutableStateOf(startingTab) }
    val mediaList = MovieRepository.getMoviesByStatus(currentTab)

    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            WatchStatusTabRow(
                currentList = currentTab,
                switchToWatching = { currentTab = WatchStatus.WATCHING },
                switchToPlanning = { currentTab = WatchStatus.PLANNING },
                switchToCompleted = { currentTab = WatchStatus.COMPLETED },
            )

            MovieList(
                navController = navController,
                list = mediaList
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewMoviesScreen() {
    EzraTheme(darkTheme = true) {
        Surface {
            MoviesScreen(rememberNavController(), rememberCoroutineScope())
        }

    }
}
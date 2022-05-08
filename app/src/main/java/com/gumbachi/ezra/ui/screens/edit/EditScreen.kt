package com.gumbachi.ezra.ui.screens.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gumbachi.ezra.model.*
import com.gumbachi.ezra.repository.EditRepository
import com.gumbachi.ezra.ui.screens.edit.components.*
import com.gumbachi.ezra.ui.theme.EzraTheme

@Composable
fun EditScreen(
    navController: NavHostController,
    media: Media,
) {

    var newScore by remember { mutableStateOf(media.score) }
    var newStatus by remember { mutableStateOf(media.status) }
    var newProgress by remember { mutableStateOf(media.episodesWatched) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxHeight(0.85F)
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            StatusDropdown(
                startingStatus = media.status,
                onValueChange = {
                    newStatus = it
                    newProgress = if (it == WatchStatus.COMPLETED) media.totalEpisodes else media.episodesWatched
                }
            )

            ScoreSlider(
                startingValue = media.score.toInt(),
                onValueChange = { newScore = it }
            )

            if (media.isEpisodic) {
                EpisodeProgressAdjuster(
                    episodesWatched = newProgress,
                    totalEpisodes = media.totalEpisodes,
                    onProgressSubtract = { if (newProgress > 0) newProgress-- },
                    onProgressAdd = { if (newProgress < media.totalEpisodes) newProgress++ }
                )
            }

            DatePicker(title = "Start and Finish")
            NotesField()
        }

        EditButtonRow(
            modifier = Modifier.padding(vertical = 20.dp),
            onSaveAction = {
                EditRepository.submit(
                    media = media,
                    newScore = newScore,
                    newProgress = newProgress,
                    newStatus = newStatus
                )
                when (media) {
                    is Movie -> navController.navigate("movies/$newStatus") { popUpTo("movies/$newStatus") }
                    is Show -> navController.navigate("tv/$newStatus") { popUpTo("tv/$newStatus") }
                    is Anime -> navController.navigate("anime/$newStatus") { popUpTo("anime/$newStatus") }
                }
            },
            onDeleteAction = {
                EditRepository.deleteMedia(media = media)
                navController.popBackStack()
            }
        )
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewEditScreen() {
    EzraTheme(darkTheme = true) {
        Surface {
            EditScreen(
                navController = rememberNavController(),
                media = Show.getDummy()
            )
        }
    }
}
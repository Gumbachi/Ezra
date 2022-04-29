package com.gumbachi.ezra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gumbachi.ezra.data.dummy.DummyData
import com.gumbachi.ezra.model.*
import com.gumbachi.ezra.repository.EditRepository
import com.gumbachi.ezra.navigation.Screen
import com.gumbachi.ezra.ui.theme.EzraTheme
import kotlinx.coroutines.newFixedThreadPoolContext
import java.util.*

@Composable
fun EditScreen(
    navController: NavHostController,
    media: Media,
) {

    var newScore by remember { mutableStateOf(media.score) }
    var newStatus by remember { mutableStateOf(media.status) }
    var newProgress by remember { mutableStateOf(media.episodesWatched) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            StatusDropdown(
                startingStatus = media.status,
                onValueChange = {
                    newStatus = it
                    newProgress = if (it == WatchStatus.COMPLETED) {
                        media.episodes
                    } else {
                        media.episodesWatched
                    }
                }
            )
            ScoreSlider(
                startingValue = media.score.toInt(),
                onValueChange = { newScore = it }
            )

            if (media.isEpisodic) {
                EpisodeProgressBox(
                    episodesWatched = newProgress,
                    totalEpisodes = media.episodes,
                    onProgressSubtract = { if (newProgress > 0) newProgress-- },
                    onProgressAdd = { if (newProgress < media.episodes) newProgress++ }
                )
            }

            EditButtonRow(
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
}

@Composable
fun StatusDropdown(
    startingStatus: WatchStatus,
    onValueChange: (WatchStatus) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }
    var currentStatus by remember { mutableStateOf(startingStatus) }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 12.dp,
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Status",
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { expanded = true }) {
                Text(
                    text = currentStatus.toString(),
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 0.dp, y = 6.dp)
                ) {
                    // Watching Dropdown Item
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(WatchStatus.WATCHING)
                            currentStatus = WatchStatus.WATCHING
                            expanded = false
                        }
                    ) { Text("Watching") }

                    // Planning Dropdown Item
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(WatchStatus.PLANNING)
                            currentStatus = WatchStatus.PLANNING
                            expanded = false
                        }
                    ) { Text("Planning") }

                    // Completed Dropdown Item
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(WatchStatus.COMPLETED)
                            currentStatus = WatchStatus.COMPLETED
                            expanded = false
                        }
                    ) { Text("Completed") }
                }
            }
        }
    }
}

@Composable
fun ScoreSlider(
    startingValue: Int,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderPosition by remember { mutableStateOf(startingValue) }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 12.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Score",
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = sliderPosition.toString(),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Slider(
                    value = sliderPosition.toFloat(),
                    onValueChange = {
                        sliderPosition = it.toInt()
                        onValueChange(it.toDouble())
                    },
                    valueRange = 0F..10F,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun EpisodeProgressBox(
    episodesWatched: Int,
    totalEpisodes: Int,
    onProgressAdd: () -> Unit,
    onProgressSubtract: () -> Unit,
    modifier: Modifier = Modifier
) {
    

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 12.dp,
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp)
        ) {
            Text(text = "Progress", fontWeight = FontWeight.Bold)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.7F)
            ) {
                // Subtract Button
                IconButton(
                    onClick = { onProgressSubtract() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) { Icon(imageVector = Icons.Filled.Remove, contentDescription = null) }

                Text(
                    text = "${episodesWatched}/${totalEpisodes}",
                    fontSize = 20.sp
                )

                // Add Button
                IconButton(
                    onClick = { onProgressAdd() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) { Icon(imageVector = Icons.Filled.Add, contentDescription = null) }
            }
        }
    }
}


@Composable
fun EditButtonRow(
    onSaveAction: () -> Unit,
    onDeleteAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { onDeleteAction() }
        ) {
            Text(text = "DELETE")
        }
        Button(
            onClick = { onSaveAction() }
        ) {
            Text(text = "SAVE")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewEditScreen() {
    EzraTheme(darkTheme = true) {
        Surface {
            EditScreen(
                navController = rememberNavController(),
                media = DummyData.show
            )
        }
    }
}
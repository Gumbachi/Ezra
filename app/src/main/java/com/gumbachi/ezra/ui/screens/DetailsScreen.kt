package com.gumbachi.ezra.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.gumbachi.ezra.AnimeDetailsQuery
import com.gumbachi.ezra.R
import com.gumbachi.ezra.data.remote.responses.movie.MovieCredits
import com.gumbachi.ezra.data.remote.responses.movie.MovieDetails
import com.gumbachi.ezra.data.remote.responses.tv.TVCredits
import com.gumbachi.ezra.data.remote.responses.tv.TVDetails
import com.gumbachi.ezra.repository.AnimeRepository
import com.gumbachi.ezra.repository.MovieRepository
import com.gumbachi.ezra.repository.TVRepository
import com.gumbachi.ezra.ui.theme.EzraTheme
import com.gumbachi.ezra.utils.convertMinutesToRuntime
import com.gumbachi.ezra.utils.getCompleteTMDBPath
import kotlinx.coroutines.delay


@Composable
fun TVDetailsScreen(
    navController: NavHostController,
    id: Int
) {

    println("TV DETAILS ID IS $id")
    var details: TVDetails? by remember { mutableStateOf(null) }
    var credits: TVCredits? by remember { mutableStateOf(null) }

    if (details != null && credits != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row {
                DetailsImageCard(imageURL = "https://image.tmdb.org/t/p/w300${details?.poster_path}")
                Spacer(modifier = Modifier.padding(start = 12.dp))
                ShortDetails(
                    title = details!!.name,
                    lines = listOf(
                        "First Aired: ${details!!.first_air_date}",
                        "${details!!.number_of_seasons} seasons, ${details!!.number_of_episodes} episodes ",
                        "Average Score: ${details!!.vote_average}",
                        "Popularity: ${details!!.popularity}",
                        "Creators: ${details!!.created_by.joinToString(separator = ", ") { it.name }}",
                        "Status: ${details!!.status}"
                    )
                )
            }
            OverviewSection(description = details!!.overview)
            ProfileSection(
                sectionTitle = "Cast",
                people = credits!!.cast.map {
                    Triple(getCompleteTMDBPath(it.profile_path), it.character, it.name)
                }
            )
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(Unit) {
        details = TVRepository.getTVDetails(id)
        credits = TVRepository.getTVCredits(id)

        if (credits == null || details == null) {
            println("SOMETHING WENT WRONG IN MOVIE DETAILS")
            navController.popBackStack()
        }
    }
}

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    id: Int
) {

    println("MOVIE DETAILS ID IS $id")
    var details: MovieDetails? by remember { mutableStateOf(null) }
    var credits: MovieCredits? by remember { mutableStateOf(null) }

    if (details != null && credits != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row {
                DetailsImageCard(imageURL = getCompleteTMDBPath(details?.poster_path))
                Spacer(modifier = Modifier.padding(start = 12.dp))
                ShortDetails(
                    title = details!!.title,
                    lines = listOf(
                        details!!.release_date,
                        convertMinutesToRuntime(details!!.runtime),
                        "Average Score: ${details!!.vote_average}",
                        "Popularity: ${details!!.popularity}",
                        "Budget: ${details!!.budget}"
                    )
                )
            }
            OverviewSection(description = details!!.overview)
            ProfileSection(
                sectionTitle = "Cast",
                people = credits!!.cast.map {
                    Triple(getCompleteTMDBPath(it.profile_path), it.character, it.name)
                }
            )
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(Unit) {
        delay(500)
        details = MovieRepository.getMovieDetails(id)
        credits = MovieRepository.getMovieCredits(id)

        if (credits == null || details == null) {
            println("SOMETHING WENT WRONG IN MOVIE DETAILS")
            navController.popBackStack()
        }
    }
}


@Composable
fun AnimeDetailsScreen(
    navController: NavHostController,
    id: Int
) {

    var details: AnimeDetailsQuery.Media? by remember { mutableStateOf(null) }

    details?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row {
                DetailsImageCard(imageURL = it.coverImage?.large ?: "")
                Spacer(modifier = Modifier.padding(start = 12.dp))
                ShortDetails(
                    title = it.title?.english ?: it.title?.romaji ?: it.title?.native ?: "Title Not Found",
                    lines = listOf(
                        "Romaji: ${it.title?.romaji ?: "None"}",
                        "Native: ${it.title?.native ?: "None"}",
                        "Status: ${it.status}",
                        "${it.episodes} Episodes",
                        "First Aired: ${it.season}, ${it.seasonYear}",
                        "Average Score: ${it.averageScore}%",
                        "Episode Duration: ${it.duration ?: 0}",
                    )
                )
            }
            OverviewSection(description = it.description ?: "Description Not Available")
            ProfileSection(
                sectionTitle = "Characters",
                people = it.characters?.edges?.map { edge ->
                    Triple(
                        edge?.node?.image?.medium ?: "",
                        edge?.node?.name?.full ?: "Name Unavailable",
                        edge?.voiceActorRoles?.getOrNull(0)?.voiceActor?.name?.full ?: "Name Unavailable"
                    )
                } ?: listOf()
            )
            ProfileSection(
                sectionTitle = "Voice Actors",
                people = it.characters?.edges?.map { edge ->
                    Triple(
                        edge?.voiceActorRoles?.getOrNull(0)?.voiceActor?.image?.medium ?: "",
                        edge?.voiceActorRoles?.getOrNull(0)?.voiceActor?.name?.full ?: "Name Unavailable",
                        edge?.node?.name?.full ?: "Name Unavailable",
                    )
                } ?: listOf()
            )
        }
    }

    if (details == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(Unit) {
        delay(500)
        details = AnimeRepository.getAnimeDetails(id)

        if (details == null) {
            println("SOMETHING WENT WRONG IN MOVIE DETAILS")
            navController.popBackStack()
        }
    }
}


@Composable
fun DetailsImageCard(
    imageURL: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageURL,
        placeholder = painterResource(R.drawable.image_not_found),
        error = painterResource(R.drawable.image_not_found),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .height(260.dp)
            .fillMaxWidth(0.4F)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Composable
fun OverviewSection(
    description: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Overview",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = description,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ProfileSection(
    sectionTitle: String,
    people: List<Triple<String, String, String>>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = sectionTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            people.forEach {
                val (profile, actor, character) = it
                PersonCard(
                    profilePath = profile,
                    name = actor,
                    character = character
                )
            }
        }
    }
}

@Composable
fun PersonCard(
    profilePath: String,
    name: String,
    character: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .width(120.dp)
            .height(190.dp)
    ) {
        Column {
            AsyncImage(
                model = profilePath,
                placeholder = painterResource(R.drawable.image_not_found),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight(0.75F)
            )
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    Text(
                        text = name,
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(4.dp),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = character,
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(4.dp),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
fun ShortDetails(
    title: String,
    lines: List<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        lines.forEach {
            Text(
                text = it,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsScreen() {
    EzraTheme(darkTheme = true) {
        Surface() {
            PersonCard(profilePath = "", name = "Howdy", character = "Dog")
        }
    }

}
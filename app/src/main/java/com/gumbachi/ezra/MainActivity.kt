package com.gumbachi.ezra

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gumbachi.ezra.model.TopBarType
import com.gumbachi.ezra.navigation.EzraNavHost
import com.gumbachi.ezra.navigation.Screen
import com.gumbachi.ezra.repository.AnimeRepository
import com.gumbachi.ezra.repository.AuthRepository
import com.gumbachi.ezra.repository.MovieRepository
import com.gumbachi.ezra.repository.TVRepository
import com.gumbachi.ezra.ui.components.*
import com.gumbachi.ezra.ui.theme.EzraTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EzraTheme {

                println("HOWDY WORLD")

                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()


                EzraApp(
                    navController = navController,
                    scaffoldState = scaffoldState,
                    scope = scope
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            println("LIFECYCLE SCOPE LAUNCH")
            if (AuthRepository.tmdbToken != null && AuthRepository.tmdbSessionID == null) {
                println("GETTING SESSION")
                AuthRepository.getTMDBSession()
            }
        }
    }
}




@Composable
fun EzraApp(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {

    var topBarType by remember { mutableStateOf(TopBarType.NONE) }
    var appBarTitle by remember { mutableStateOf("") }
    var currentScreen by remember { mutableStateOf("MOVIES") }

    val context = LocalContext.current

    
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            when (topBarType) {
                TopBarType.FULL ->
                    FullAppBar(
                        appBarTitle = appBarTitle,
                        navButtonAction = {
                            scope.launch {
                                if (scaffoldState.drawerState.isClosed) {
                                    scaffoldState.drawerState.open()
                                } else {
                                    scaffoldState.drawerState.close()
                                }
                            }
                        },
                        searchButtonAction = {
                            MovieRepository.clearSearchResults()
                            TVRepository.clearSearchResults()
                            AnimeRepository.clearSearchResults()
                            navController.navigate("search/$currentScreen")
                        },
                        sortButtonAction = { /* TODO */ }
                    )
                TopBarType.PARTIAL ->
                    PartialAppBar(
                        appBarTitle = appBarTitle,
                        backAction = { navController.popBackStack() }
                    )
                TopBarType.NONE -> {}
            }
        },
        drawerContent = {
            NavHeader(Modifier.padding(16.dp))
            Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))

            // Movie Navigation Button
            DrawerButton(
                icon = Icons.Filled.Movie,
                label = "Movies",
                isSelected = currentScreen == "MOVIES",
                action = {
                    navController.navigate("movies/WATCHING")
                    currentScreen = "MOVIES"
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )

            // TV Navigation Button
            DrawerButton(
                icon = Icons.Filled.Tv,
                label = "TV",
                isSelected = currentScreen == "TV",
                action = {
                    navController.navigate("tv/WATCHING")
                    currentScreen = "TV"
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )

            // Anime Navigation Button
            DrawerButton(
                icon = Icons.Filled.Animation,
                label = "Anime",
                isSelected = currentScreen == "ANIME",
                action = {
                    navController.navigate("anime/WATCHING")
                    currentScreen = "ANIME"
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )



            // TMDB Auth Button
            DrawerButton(
                icon = if (AuthRepository.tmdbSessionID != null) Icons.Default.CloudDone else Icons.Default.Cloud,
                label = "Connect TMDB",
                isSelected = false,
                action = {
                    scope.launch {
                        AuthRepository.generateTMDBToken()
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.themoviedb.org/authenticate/${AuthRepository.tmdbToken}")
                        )
                        context.startActivity(intent)
                    }
                },
            )
        },
        drawerGesturesEnabled = true,
        drawerBackgroundColor = Color.Black
    ) { paddingValues ->
        EzraNavHost(
            navController = navController,
            scope = scope,
            setTopBarType = { topBarType = it },
            setTopBarTitle = { appBarTitle = it },
            modifier = Modifier.padding(paddingValues)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewEzraApp() {
    EzraTheme(darkTheme = true) {
        Surface {

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()

            EzraApp(
                navController = rememberNavController(),
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
    }
}
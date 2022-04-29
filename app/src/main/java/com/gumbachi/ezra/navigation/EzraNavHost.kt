package com.gumbachi.ezra.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gumbachi.ezra.repository.EditRepository
import com.gumbachi.ezra.model.TopBarType
import com.gumbachi.ezra.model.WatchStatus
import com.gumbachi.ezra.ui.screens.*
import kotlinx.coroutines.CoroutineScope

sealed class Screen(val route: String) {
    object Movies : Screen("movies/{startingTab}")
    object Anime : Screen("anime/{startingTab}")
    object TV : Screen("tv/{startingTab}")
    object Edit : Screen("edit")

    object MovieSearch : Screen("search/movies")
    object TVSearch : Screen("search/tv")
    object AnimeSearch : Screen("search/anime")

    object MovieDetails : Screen("details/movies/{id}")
    object TVDetails : Screen("details/tv/{id}")
    object AnimeDetails : Screen("details/anime/{id}")

}

@Composable
fun EzraNavHost(
    navController: NavHostController,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    setTopBarType: (TopBarType) -> Unit = {},
    setTopBarTitle: (String) -> Unit = {},

    ) {
    NavHost(
        navController = navController,
        startDestination = Screen.Movies.route,
    ) {

        // Main Movies List
        composable(
            Screen.Movies.route,
            arguments = listOf(navArgument("startingTab") {
                type = NavType.StringType
                defaultValue = "WATCHING"
            })
        ) {
            setTopBarTitle("Movies")
            setTopBarType(TopBarType.FULL)
            MoviesScreen(
                navController = navController,
                scope = scope,
                startingTab = WatchStatus.valueOf(
                    it.arguments?.getString("startingTab") ?: "WATCHING"
                )
            )
        }

        // Main TV List
        composable(
            Screen.TV.route,
            arguments = listOf(navArgument("startingTab") {
                type = NavType.StringType
                defaultValue = "WATCHING"
            })
        ) {
            setTopBarTitle("TV Shows")
            setTopBarType(TopBarType.FULL)
            TVScreen(
                navController = navController,
                scope = scope,
                startingTab = WatchStatus.valueOf(
                    it.arguments?.getString("startingTab") ?: "WATCHING"
                )
            )
        }

        // Main Anime List
        composable(
            Screen.Anime.route,
            arguments = listOf(navArgument("startingTab") {
                type = NavType.StringType
                defaultValue = "WATCHING"
            })
        ) {
            setTopBarTitle("Anime")
            setTopBarType(TopBarType.FULL)
            AnimeScreen(
                navController = navController,
                scope = scope,
                startingTab = WatchStatus.valueOf(
                    it.arguments?.getString("startingTab") ?: "WATCHING"
                )
            )
        }

        // Edit Routes
        composable(Screen.Edit.route) {
            setTopBarTitle("Editing: ${EditRepository.underEdit.title}")
            setTopBarType(TopBarType.PARTIAL)

            EditScreen(
                navController = navController,
                media = EditRepository.underEdit
            )
        }

        //########################## Details Routes ##########################

        composable(
            Screen.MovieDetails.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            setTopBarTitle("Movie Details")
            setTopBarType(TopBarType.PARTIAL)
            MovieDetailsScreen(
                navController = navController,
                id = it.arguments?.getInt("id") ?: 0
            )
        }

        composable(
            Screen.TVDetails.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            setTopBarTitle("TV Show Details")
            setTopBarType(TopBarType.PARTIAL)
            TVDetailsScreen(
                navController = navController,
                id = it.arguments?.getInt("id") ?: 0
            )
        }

        composable(
            Screen.AnimeDetails.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            setTopBarTitle("Anime Details")
            setTopBarType(TopBarType.PARTIAL)
            AnimeDetailsScreen(
                navController = navController,
                id = it.arguments?.getInt("id") ?: 0
            )
        }

        //########################## Search Routes ##########################

        composable(Screen.MovieSearch.route) {
            setTopBarType(TopBarType.NONE)
            MovieSearchScreen(navController = navController, scope = scope)
        }
        composable(Screen.TVSearch.route) {
            setTopBarType(TopBarType.NONE)
            TVSearchScreen(navController = navController, scope = scope)
        }
        composable(Screen.AnimeSearch.route) {
            setTopBarType(TopBarType.NONE)
            AnimeSearchScreen(navController = navController, scope = scope)
        }

    }
}
package com.gumbachi.ezra.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gumbachi.ezra.repository.AnimeRepository
import com.gumbachi.ezra.repository.MovieRepository
import com.gumbachi.ezra.repository.TVRepository
import com.gumbachi.ezra.ui.components.AnimeSearchResultList
import com.gumbachi.ezra.ui.components.MovieSearchResultList
import com.gumbachi.ezra.ui.components.TVSearchResultList
import com.gumbachi.ezra.ui.theme.EzraTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MovieSearchScreen(
    navController: NavHostController,
    scope: CoroutineScope,
) {
    SearchScreen(
        navController = navController,
        hint = "Search Movies...",
        searchFor = {
            scope.launch {
                MovieRepository.searchMovies(it)
            }
        }
    ) {
        MovieSearchResultList(
            navController = navController,
            scope = scope,
            list = MovieRepository.getSearchResults()
        )
    }
}

@Composable
fun TVSearchScreen(
    navController: NavHostController,
    scope: CoroutineScope,
) {
    SearchScreen(
        navController = navController,
        hint = "Search TV Shows...",
        searchFor = {
            scope.launch {
                TVRepository.searchTVShows(it)
            }
        }
    ) {
        TVSearchResultList(
            navController = navController,
            scope = scope,
            list = TVRepository.getSearchResults()
        )
    }
}

@Composable
fun AnimeSearchScreen(
    navController: NavHostController,
    scope: CoroutineScope,
) {
    SearchScreen(
        navController = navController,
        hint = "Search Anime...",
        searchFor = {
            scope.launch {
                AnimeRepository.searchAnime(it)
            }
        }
    ) {
        AnimeSearchResultList(
            navController = navController,
            scope = scope,
            list = AnimeRepository.getSearchResults()
        )
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    hint: String,
    searchFor: (String) -> Unit,
    contentList: @Composable () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column {
        SearchableAppBar(
            hint = hint,
            focusRequester = focusRequester,
            onBackClicked = {
                keyboardController?.hide()
                navController.popBackStack()
            },
            onSearchClicked = {
                keyboardController?.hide()
                focusManager.clearFocus()
                searchFor(it)
            },
        )
        contentList() // List composables found in MediaList.kt
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

}

@Composable
fun SearchableAppBar(
    hint: String,
    focusRequester: FocusRequester,
    onBackClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    ) {

    var text by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.Black
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = hint,
                    color = Color.White.copy(alpha = 0.5F)
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = { onBackClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = if (text.isNotEmpty()) {
                {
                    IconButton(
                        onClick = { text = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = Color.White
                        )
                    }
                }
            } else {
                {}
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    text = ""
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    EzraTheme(darkTheme = true) {
        Surface {
            AnimeSearchScreen(
                navController = rememberNavController(),
                scope = rememberCoroutineScope()
            )
        }
    }
}
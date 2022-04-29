package com.gumbachi.ezra.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.ezra.ui.theme.EzraTheme

@Composable
fun FullAppBar(
    appBarTitle: String,
    navButtonAction: () -> Unit,
    searchButtonAction: () -> Unit,
    sortButtonAction: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = appBarTitle) },
        navigationIcon = {
            IconButton(onClick = { navButtonAction() }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { sortButtonAction() }) {
                Icon(Icons.Filled.Sort, contentDescription = "Sort")
            }
            IconButton(onClick = { searchButtonAction() }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        },
        backgroundColor = Color.Black
    )
}

@Composable
fun PartialAppBar(
    appBarTitle: String,
    backAction: () -> Unit
) {
    TopAppBar(
        title = { Text(text = appBarTitle) },
        navigationIcon = {
            IconButton(onClick = { backAction() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        backgroundColor = Color.Black
    )
}



@Preview
@Composable
fun PreviewFullAppBar() {
    EzraTheme(darkTheme = true) {
        Surface {
            FullAppBar(
                appBarTitle = "App Bar",
                navButtonAction = {},
                searchButtonAction = {},
                sortButtonAction = {}
            )
        }

    }
}

@Preview
@Composable
fun PreviewPartialAppBar() {
    EzraTheme(darkTheme = true) {
        Surface() {
            PartialAppBar(
                appBarTitle = "App Bar",
                backAction = {},
            )
        }
    }
}

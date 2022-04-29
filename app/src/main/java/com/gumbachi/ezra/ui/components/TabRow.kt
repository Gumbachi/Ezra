package com.gumbachi.ezra.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.ezra.model.WatchStatus
import com.gumbachi.ezra.ui.theme.EzraTheme

@Composable
fun WatchStatusTabRow(
    currentList: WatchStatus,
    switchToWatching: () -> Unit,
    switchToPlanning: () -> Unit,
    switchToCompleted: () -> Unit
) {
    val selectedIndex = when(currentList) {
        WatchStatus.WATCHING -> 0
        WatchStatus.PLANNING -> 1
        WatchStatus.COMPLETED -> 2
    }

    Column {
        TabRow(
            selectedTabIndex = selectedIndex,
            backgroundColor = Color.Black
        ) {
            WatchStatus.values().forEachIndexed { index, title ->
                Tab(
                    text = { Text(title.toString()) },
                    selected = selectedIndex == index,
                    onClick = {
                        when(index) {
                            0 -> switchToWatching()
                            1 -> switchToPlanning()
                            2 -> switchToCompleted()
                        }
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewTabRow() {
    EzraTheme(darkTheme = true) {
        Surface() {
            WatchStatusTabRow(
                WatchStatus.PLANNING,
                {}, {}, {}
            )
        }

    }
}
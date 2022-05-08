package com.gumbachi.ezra.ui.screens.edit.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.gumbachi.ezra.model.WatchStatus

@Composable
fun StatusDropdown(
    startingStatus: WatchStatus,
    expandedByDefault: Boolean = false,
    onValueChange: (WatchStatus) -> Unit = {}
) {

    var expanded by remember { mutableStateOf(expandedByDefault) }
    var currentStatus by remember { mutableStateOf(startingStatus) }

    EditModule(title = "Status") {
        TextButton(
            modifier = Modifier.fillMaxSize(),
            onClick = { expanded = true }
        ) {
            Text(
                text = currentStatus.toString(),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(x = 0.dp, y = 6.dp)
            ) {

                WatchStatus.values().forEach {
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(it)
                            currentStatus = it
                            expanded = false
                        }
                    ) { Text(it.toString()) }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStatusDropdown() {
    StatusDropdown(startingStatus = WatchStatus.WATCHING)
}
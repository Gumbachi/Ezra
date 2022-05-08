package com.gumbachi.ezra.ui.screens.edit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.ezra.ui.theme.EzraTheme

@Composable
fun EditButtonRow(
    onSaveAction: () -> Unit = {},
    onDeleteAction: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        TextButton(
            onClick = { onDeleteAction() },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Red,
                backgroundColor = Color.Transparent
            )
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

@Preview
@Composable
fun PreviewEditButtonRow() {
    EzraTheme(darkTheme = false) {
        EditButtonRow()
    }

}
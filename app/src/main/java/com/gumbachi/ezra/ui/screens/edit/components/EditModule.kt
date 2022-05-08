package com.gumbachi.ezra.ui.screens.edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EditModule(
    title: String,
    modifier: Modifier = Modifier,
    cardHeight: Dp = 90.dp,
    resizable: Boolean = false,
    content: @Composable () -> Unit
) {

    val cardModifier = if (resizable) {
        modifier.fillMaxWidth()
    } else {
        modifier.fillMaxWidth().height(cardHeight)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 12.dp,
        modifier = cardModifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxHeight(0.25F)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun PreviewEditModule() {
    EditModule(title = "Title") {
        Text(text = "HOWDY")
    }
}
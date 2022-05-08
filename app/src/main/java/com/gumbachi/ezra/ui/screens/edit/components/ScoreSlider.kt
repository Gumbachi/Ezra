package com.gumbachi.ezra.ui.screens.edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScoreSlider(
    startingValue: Int,
    onValueChange: (Double) -> Unit = {}
) {
    var sliderPosition by remember { mutableStateOf(startingValue) }

    EditModule(title = "Score") {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
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
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun PreviewScoreSlider() {
    ScoreSlider(startingValue = 5)
}
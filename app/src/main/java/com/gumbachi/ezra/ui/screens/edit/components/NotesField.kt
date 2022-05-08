package com.gumbachi.ezra.ui.screens.edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notes
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.ezra.ui.theme.EzraTheme

@Composable
fun NotesField(
    value: String = ""
) {

    var text by remember { mutableStateOf(value) }

    EditModule(
        title = "Notes",
        resizable = true
    ) {
        TextField(
            value = text,
            leadingIcon = { Icon(imageVector = Icons.Default.Notes, contentDescription = "Notes") },
            onValueChange = { text = it },
            singleLine = false,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
fun PreviewNotesField() {
    EzraTheme(darkTheme = true) {
        NotesField(value = "")
    }

}
package com.gumbachi.ezra.ui.screens.edit.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.ezra.R
import com.gumbachi.ezra.ui.theme.EzraTheme
import java.util.*

@Composable
fun DatePicker(
    title: String = "Date",
    defaultStartDate: String = "--/--/----",
    defaultEndDate: String = "--/--/----",
) {

    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    var startDate by remember { mutableStateOf(defaultStartDate) }
    var endDate by remember { mutableStateOf(defaultEndDate) }

    val startDateDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            startDate = "$month/$dayOfMonth/$year"
        }, year, month, day
    )

    val endDateDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            endDate = "$month/$dayOfMonth/$year"
        }, year, month, day
    )


    EditModule(title = title) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(onClick = { startDateDialog.show() }) {
                Text(
                    text = startDate,
                    color = MaterialTheme.colors.onSurface
                )
            }

            OutlinedButton(onClick = { endDateDialog.show() }) {
                Text(
                    text = endDate,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }


    }
}

@Preview
@Composable
fun PreviewDatePicker() {
    EzraTheme(darkTheme = true) {
        DatePicker("Start/End Date")
    }

}
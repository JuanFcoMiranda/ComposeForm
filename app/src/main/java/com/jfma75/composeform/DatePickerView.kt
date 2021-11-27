package com.jfma75.composeform

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerView(
    datePicked : String,
    updatedDate : (date : Long) -> Unit,
) {
    val activity = LocalContext.current as? AppCompatActivity

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDatePicker(
        activity: AppCompatActivity?,
        updatedDate: (date: Long) -> Unit
    ) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.apply {
            val selectedDate = dateFormatter(datePicked)
            set(Calendar.YEAR, selectedDate.year)
            set(Calendar.MONTH, selectedDate.monthValue-1)
            set(Calendar.DAY_OF_MONTH, selectedDate.dayOfMonth)
        }
        MaterialDatePicker.Builder.datePicker()
            .setSelection(calendar.timeInMillis).build().apply {
                activity?.supportFragmentManager?.let { supportFragmentManager ->
                    show(supportFragmentManager, "Tag")
                    addOnPositiveButtonClickListener {
                        updatedDate(it)
                    }
                }
            }
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, MaterialTheme.colors.surface)
            .clickable {
                showDatePicker(activity, updatedDate)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text= datePicked,
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    //.fillMaxWidth()
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = MaterialTheme.colors.surface
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(localDate: LocalDate): String {
    localDate.let {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
        return formatter.format(localDate)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(stringDate: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
    return LocalDate.parse(stringDate, formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(longDate: Long): String {
    val date: LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(longDate), ZoneId.systemDefault())
    return dateFormatter(date.toLocalDate())
}
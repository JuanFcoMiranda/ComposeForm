package com.jfma75.composeform.components

import android.os.Build
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import com.jfma75.composeform.screens.InputWrapper
import java.lang.Exception
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.ofInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerView(
    datePicked : InputWrapper,
    onValueChange : (date : Long) -> Unit,
) {
    val activity = LocalContext.current as? AppCompatActivity

    val fieldValue = remember {
        mutableStateOf(TextFieldValue(datePicked.value, TextRange(datePicked.value.length)))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDatePicker(
        activity: AppCompatActivity?
    ) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.apply {
            val selectedDate = dateFormatter(fieldValue.value.text)
            set(Calendar.YEAR, selectedDate.year)
            set(Calendar.MONTH, selectedDate.monthValue-1)
            set(Calendar.DAY_OF_MONTH, selectedDate.dayOfMonth)
        }
        MaterialDatePicker.Builder.datePicker()
            .setSelection(calendar.timeInMillis).build().apply {
                activity?.supportFragmentManager?.let { supportFragmentManager ->
                    show(supportFragmentManager, "Tag")
                    addOnPositiveButtonClickListener {
                        fieldValue.value = TextFieldValue(text = dateFormatter(it))
                        onValueChange(it)
                    }
                }
            }
    }
    Column {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    1.dp, if (datePicked.errorId != null) {
                        MaterialTheme.colors.error
                    } else {
                        MaterialTheme.colors.surface
                    }
                )
                .clickable {
                    showDatePicker(activity)
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = fieldValue.value.text,
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
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
        if (datePicked.errorId != null) {
            Text(
                text = stringResource(datePicked.errorId),
                color = MaterialTheme.colors.surface,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(horizontal = 16.dp)
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
    if(stringDate.isBlank()) return LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
    return LocalDate.parse(stringDate, formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(longDate: Long): String {
    val date: LocalDateTime = ofInstant(Instant.ofEpochMilli(longDate), ZoneId.systemDefault())
    return dateFormatter(date.toLocalDate())
}

@RequiresApi(Build.VERSION_CODES.O)
fun isValidDateTime(stringDate: String) : Boolean {
    if(stringDate.isBlank()) return false
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
    return try {
        val date = LocalDate.parse(stringDate, formatter)
        date != null
    } catch (ex: Exception) {
        false
    }

}
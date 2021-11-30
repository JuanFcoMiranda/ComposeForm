package com.jfma75.composeform.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jfma75.composeform.R
import com.jfma75.composeform.screens.InputWrapper

@Composable
fun CustomTextField(
    modifier: Modifier,
    inputWrapper: InputWrapper,
    @StringRes labelResId: Int,
    @StringRes placeHolderResId: Int,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        capitalization = KeyboardCapitalization.None,
        autoCorrect = true,
        keyboardType = KeyboardType.Email
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (value: String) -> Unit,
    onImeKeyAction: () -> Unit
) {
    val fieldValue = remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }
    Column {
        OutlinedTextField(
            modifier = modifier,
            label = {
                Text(
                    text = stringResource(labelResId),
                    style = TextStyle(
                        color = MaterialTheme.colors.surface,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(placeHolderResId),
                    style = TextStyle(
                        color = MaterialTheme.colors.surface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.surface,
                unfocusedBorderColor = MaterialTheme.colors.surface,
                focusedLabelColor = MaterialTheme.colors.surface,
                cursorColor = MaterialTheme.colors.surface,
                textColor = MaterialTheme.colors.surface
            ),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = inputWrapper.errorId != null,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onAny = { onImeKeyAction() }),
            visualTransformation = visualTransformation,
            onValueChange = {
                fieldValue.value = it
                onValueChange(it.text)
            },
            value = fieldValue.value
        )
        if (inputWrapper.errorId != null) {
            Text(
                text = stringResource(inputWrapper.errorId),
                color = MaterialTheme.colors.surface,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
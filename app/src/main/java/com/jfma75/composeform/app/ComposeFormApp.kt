package com.jfma75.composeform.app

import androidx.compose.runtime.Composable
import com.jfma75.composeform.ui.theme.ComposeFormTheme

@Composable
fun ComposeFormApp(content: @Composable () -> Unit) {
    ComposeFormTheme {
        content()
    }
}
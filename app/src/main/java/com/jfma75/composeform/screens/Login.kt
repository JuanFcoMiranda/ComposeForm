package com.jfma75.composeform.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.jfma75.composeform.DatePickerView
import com.jfma75.composeform.R
import com.jfma75.composeform.dateFormatter
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.user_profile)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        restartOnPlay = true
    )
    val focusManager = LocalFocusManager.current
    val (text, onTextChanged) = rememberSaveable { mutableStateOf("") }
    val (pass, onPassChanged) = rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val datePicked = rememberSaveable { mutableStateOf(dateFormatter(LocalDate.now())) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {
        LottieAnimation(
            composition,
            progress
        )
        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 24.dp))
                    .clipToBounds()
                    .align(Alignment.Center)
                    .background(color = MaterialTheme.colors.primary.copy(alpha = 0.8f))
                    .padding(all = 16.dp)
                    .imePadding()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = stringResource(id = R.string.email),
                            style = TextStyle(
                                color = MaterialTheme.colors.surface,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_email),
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
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    //visualTransformation = NumberVisualTransformation(),
                    onValueChange = onTextChanged,
                    value = text
                )

                OutlinedTextField(
                    label = {
                        Text(
                            text = stringResource(id = R.string.enter_password),
                            style = TextStyle(
                                color = MaterialTheme.colors.surface,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_your_password),
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
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        val image = when {
                            passwordVisibility -> Icons.Filled.Visibility
                            else -> Icons.Filled.VisibilityOff
                        }

                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                imageVector = image,
                                contentDescription = stringResource(id = R.string.password_visibility),
                                tint = MaterialTheme.colors.surface
                            )
                        }
                    },
                    visualTransformation = when {
                        passwordVisibility -> VisualTransformation.None
                        else -> PasswordVisualTransformation()
                    },
                    onValueChange = onPassChanged,
                    value = pass
                )

                DatePickerView(datePicked.value) { selectedDate ->
                    datePicked.value = dateFormatter(selectedDate).toString()
                }

                Button(onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = MaterialTheme.colors.surface
                    ),
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f)
                ) {
                    Text(
                        text = "Submit",
                        style = TextStyle(
                            color = MaterialTheme.colors.surface,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun LoginScreen_Preview() {
    LoginScreen()
}
package com.jfma75.composeform.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.airbnb.lottie.compose.*
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.jfma75.composeform.*
import com.jfma75.composeform.R
import com.jfma75.composeform.components.CustomTextField
import com.jfma75.composeform.components.DatePickerView
import com.jfma75.composeform.components.dateFormatter
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@InternalCoroutinesApi
@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(viewModel : LoginScreenViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.user_profile)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        restartOnPlay = true
    )

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.RESUMED
        )
    }

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val date by viewModel.datePicked.collectAsState()
    var passwordVisibility by remember { mutableStateOf(false) }

    val creditCardNumberFocusRequester = remember { FocusRequester() }
    val nameFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ScreenEvent.ShowToast -> context.toast(event.messageId)
                is ScreenEvent.UpdateKeyboard -> {
                    if (event.show) keyboardController?.show() else keyboardController?.hide()
                }
                is ScreenEvent.ClearFocus -> focusManager.clearFocus()
                is ScreenEvent.RequestFocus -> {
                    when (event.textFieldKey) {
                        FocusedTextFieldKey.EMAIL -> nameFocusRequester.requestFocus()
                        FocusedTextFieldKey.PASSWORD -> creditCardNumberFocusRequester.requestFocus()
                        else -> {}
                    }
                }
                is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
            }
        }
    }

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
                CustomTextField(
                    modifier = Modifier
                        .focusRequester(nameFocusRequester)
                        .onFocusChanged { focusState ->
                            viewModel.onTextFieldFocusChanged(
                                key = FocusedTextFieldKey.EMAIL,
                                isFocused = focusState.isFocused
                            )
                        },
                    labelResId = R.string.email,
                    placeHolderResId = R.string.enter_email,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    inputWrapper = email,
                    onValueChange = viewModel::onEmailEntered,
                    onImeKeyAction = viewModel::onNameImeActionClick
                )

                CustomTextField(
                    modifier = Modifier
                        .focusRequester(nameFocusRequester)
                        .onFocusChanged { focusState ->
                            viewModel.onTextFieldFocusChanged(
                                key = FocusedTextFieldKey.EMAIL,
                                isFocused = focusState.isFocused
                            )
                        },
                    labelResId = R.string.enter_password,
                    placeHolderResId = R.string.enter_your_password,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation =  when {
                        passwordVisibility -> VisualTransformation.None
                        else -> PasswordVisualTransformation()
                    },
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
                    inputWrapper = password,
                    onValueChange = viewModel::onPasswordEntered,
                    onImeKeyAction = viewModel::onContinueClick
                )

                DatePickerView(datePicked = date, onValueChange = viewModel::onDateTimeEntered)

                Button(onClick = viewModel::onContinueClick,
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

@InternalCoroutinesApi
@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun LoginScreen_Preview() {
    LoginScreen()
}


fun Context.toast(messageId: Int) {
    Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show()
}
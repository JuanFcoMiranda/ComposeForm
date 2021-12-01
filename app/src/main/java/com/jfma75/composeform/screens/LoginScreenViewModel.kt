package com.jfma75.composeform.screens

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfma75.composeform.*
import com.jfma75.composeform.components.dateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import javax.inject.Inject

private class InputErrors(
    val emailErrorId: Int?,
    val passwordErrorId: Int?,
    val dateTimeErrorId: Int?
)

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val email = savedStateHandle.getStateFlow(viewModelScope, "email", InputWrapper())
    val password = savedStateHandle.getStateFlow(viewModelScope, "password", InputWrapper())
    val datePicked = savedStateHandle.getStateFlow(viewModelScope, "date", InputWrapper())

    private var focusedTextField = savedStateHandle.get("focusedTextField") ?: FocusedTextFieldKey.EMAIL
        set(value) {
            field = value
            savedStateHandle.set("focusedTextField", value)
        }

    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()

    fun onEmailEntered(input: String) {
        email.tryEmit(email.value.copy(value = input, errorId = null))
    }

    fun onPasswordEntered(input: String) {
        password.value = password.value.copy(value = input, errorId = null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateTimeEntered(input: Long) {
        datePicked.value = datePicked.value.copy(value = dateFormatter(input), errorId = null)
    }

    fun onNameImeActionClick() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.MoveFocus())
        }
    }

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onContinueClick() {
        viewModelScope.launch(Dispatchers.Default) {
            when (val inputErrors = getInputErrors()) {
                null -> {
                    clearFocusAndHideKeyboard()
                    _events.send(ScreenEvent.ShowToast(R.string.success))
                }
                else -> displayInputErrors(inputErrors)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getInputErrors(): InputErrors? {
        val emailWrapper = email.value
        val passwordWrapper = password.value
        val dateTimeWrapper = datePicked.value

        val emailError = InputValidator.getEmailError(emailWrapper.value)
        val passwordError = InputValidator.getPasswordError(passwordWrapper.value)
        val dateTimeError = InputValidator.getDateTimeError(dateTimeWrapper.value)

        return if (emailError == null && passwordError == null && dateTimeError == null) {
            null
        } else {
            InputErrors(emailError, passwordError, dateTimeError)
        }
    }

    private suspend fun displayInputErrors(inputErrors: InputErrors) {
        email.emit(email.value.copy(errorId = inputErrors.emailErrorId))
        password.emit(password.value.copy(errorId = inputErrors.passwordErrorId))
        datePicked.emit(datePicked.value.copy(errorId = inputErrors.dateTimeErrorId))
    }

    private suspend fun clearFocusAndHideKeyboard() {
        _events.send(ScreenEvent.ClearFocus)
        _events.send(ScreenEvent.UpdateKeyboard(false))
        focusedTextField = FocusedTextFieldKey.NONE
    }
}

@Parcelize
data class InputWrapper(
    var value: String = "",
    val errorId: Int? = null
) : Parcelable
package com.jfma75.composeform.screens

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfma75.composeform.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

private class InputErrors(
    val emailErrorId: Int?,
    val passwordErrorId: Int?
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

    fun onNameEntered(input: String) {
        email.tryEmit(email.value.copy(value = input, errorId = null))
    }

    fun onNameImeActionClick() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.MoveFocus())
        }
    }

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }

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

    private fun getInputErrors(): InputErrors? {
        val emailWrapper = email.value
        val passwordWrapper = password.value
        val emailError = InputValidator.getEmailError(emailWrapper.value)
        val passwordError = InputValidator.getPasswordError(passwordWrapper.value)
        return if (emailError == null && passwordError == null) {
            null
        } else {
            InputErrors(emailError, passwordError)
        }
    }

    private suspend fun displayInputErrors(inputErrors: InputErrors) {
        email.emit(email.value.copy(errorId = inputErrors.emailErrorId))
        password.emit(password.value.copy(errorId = inputErrors.passwordErrorId))
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
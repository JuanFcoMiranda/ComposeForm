package com.jfma75.composeform

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InputValidationAutoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

   /* val name = savedStateHandle.getStateFlow(viewModelScope, "name", InputWrapper())
    val creditCardNumber = savedStateHandle.getStateFlow(viewModelScope, "creditCardNumber", InputWrapper())
    val areInputsValid = combine(name, creditCardNumber) { name, cardNumber ->
        name.value.isNotEmpty() && name.errorId == null && cardNumber.value.isNotEmpty() && cardNumber.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()

    fun onNameEntered(input: String) {
        val errorId = InputValidator.getNameErrorIdOrNull(input)
        name.tryEmit(name.value.copy(value = input, errorId = errorId))
    }

    fun onCardNumberEntered(input: String) {
        val errorId = InputValidator.getCardNumberErrorIdOrNull(input)
        creditCardNumber.tryEmit(creditCardNumber.value.copy(value = input, errorId = errorId))
    }

    fun onContinueClick() {
        viewModelScope.launch(Dispatchers.Default) {
            val resId = if (areInputsValid.value) R.string.success else R.string.validation_error
            _events.send(ScreenEvent.ShowToast(resId))
        }
    }*/
}

fun <T> SavedStateHandle.getStateFlow(
    scope: CoroutineScope,
    key: String,
    initialValue: T
): MutableStateFlow<T> {
    val liveData = getLiveData(key, initialValue)
    val stateFlow = MutableStateFlow(initialValue)

    val observer = Observer<T> { value ->
        if (value != stateFlow.value) {
            stateFlow.value = value
        }
    }
    liveData.observeForever(observer)

    stateFlow.onCompletion {
        withContext(Dispatchers.Main.immediate) {
            liveData.removeObserver(observer)
        }
    }.onEach { value ->
        withContext(Dispatchers.Main.immediate) {
            if (liveData.value != value) {
                liveData.value = value
            }
        }
    }.launchIn(scope)

    return stateFlow
}


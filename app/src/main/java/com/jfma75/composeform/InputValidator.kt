package com.jfma75.composeform

import java.util.regex.Pattern

object InputValidator {
    fun getEmailError(input: String): Int? {
        return when {
            input.isBlank() || !isValidEmail(input) -> R.string.email_incorrect
            else -> null
        }
    }

    fun getPasswordError(input: String): Int? {
        return when {
            input.length < 8 -> R.string.password_too_short
            else -> null
        }
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}
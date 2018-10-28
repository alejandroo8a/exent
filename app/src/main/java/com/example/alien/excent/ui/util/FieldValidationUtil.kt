package com.example.alien.excent.ui.util

import android.util.Patterns
import android.text.TextUtils
import java.util.regex.Pattern
import javax.inject.Inject



class FieldValidationUtil @Inject
internal constructor(){

    private val NON_NUMERIC_TEXT = Pattern.compile("[A-Za-z\\s-]+")
    private val ZIP_CODE = Pattern.compile("[0-9]{5}")
    private val PHONE_NUMBER = Pattern.compile("([2-9][0-9]{2})[-. ]?([0-9]{3})[-. ]?([0-9]{4})")
    private val UPPERCASE = Pattern.compile("[A-Z]")
    private val LOWERCASE = Pattern.compile("[a-z]")
    private val NUMBERS = Pattern.compile("[0-9]")
    private val PASSWORD_MIN_LENGTH = 8
    private val PASSWORD_MAX_LENGTH = 50

    fun isEmpty(input: String): Boolean {
        return TextUtils.isEmpty(input)
    }

    fun isShorterThan(input: String?, length: Int): Boolean {
        return input != null && input.length < length
    }

    fun isLongerThan(input: String?, length: Int): Boolean {
        return input != null && input.length > length
    }

    fun isNonNumericText(input: String): Boolean {
        return NON_NUMERIC_TEXT.matcher(input).matches()
    }

    fun validateEmail(email: String): Boolean {
        return !isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validateZipCode(input: String): Boolean {
        return !isEmpty(input) && ZIP_CODE.matcher(input).matches()
    }

    fun validatePhone(input: String): Boolean {
        return !isEmpty(input) && PHONE_NUMBER.matcher(input).matches()
    }

    fun validatePasswordFormat(input: String): Boolean {
        return (!isEmpty(input)
                && !isShorterThan(input, PASSWORD_MIN_LENGTH)
                && !isLongerThan(input, PASSWORD_MAX_LENGTH)
                && UPPERCASE.matcher(input).find()
                && LOWERCASE.matcher(input).find()
                && NUMBERS.matcher(input).find())
    }

}
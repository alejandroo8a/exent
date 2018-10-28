package com.example.alien.excent.ui.util.forms

import android.support.annotation.NonNull
import com.example.alien.excent.R
import com.example.alien.excent.ui.util.FieldValidationUtil
import java.util.*
import javax.inject.Inject

class FormHelper @Inject
internal constructor(private val fieldValidationUtil: FieldValidationUtil) {

    val FIELD_LENGTH_MAX_USER = 4
    val FIELD_LENGTH_MAX_PASSWORD = 8

    lateinit var callback: FormCallback

    private val fieldValidationMap: EnumMap<FormField, Boolean> = EnumMap(FormField::class.java)
    private val NON_REQUIRED_FIELDS = emptyList<FormField>()


    fun initialize(@NonNull callback: FormCallback, fieldList: List<FormField>) {
        this.callback = callback

        for (field in fieldList) {
            fieldValidationMap[field] = false

            // init as true, since these fields are not required
            if (NON_REQUIRED_FIELDS.contains(field)) {
                fieldValidationMap[field] = true
            }
        }
    }

    private fun assertInitialized() {
        if (callback == null) {
            throw IllegalStateException("You've attempted to use FormHelper without first calling formHelper.initialize()")
        }
    }

    fun validateField(input: FormInput): Boolean {
        assertInitialized()

        var validated = validateRequired(input)
        if (validated) {
            validated = validateLength(input)
        }
        if (validated) {
            validated = validateSpecifics(input)
        }

        setFieldValidated(input, validated)
        return validated
    }

    private fun validateRequired(input: FormInput): Boolean {
        when (input.field) {
            FormField.USER,
            FormField.EMAIL,
            FormField.PASSWORD,
            FormField.PASSWORD_CONFIRM
            -> if (fieldValidationUtil.isEmpty(
                    input.value
                )
            ) {
                val field = when(input.field) {
                    FormField.USER -> "usuario"
                    FormField.EMAIL -> "email"
                    FormField.PASSWORD -> "contraseña"
                    FormField.PASSWORD_CONFIRM -> "confirmar contraseña"
                }
                callback.formError(FormError(input.field, R.string.field_is_required, field))
                return false
            }
        }
        return true
    }

    private fun validateLength(input: FormInput): Boolean {
        when (input.field) {
            FormField.USER -> if (fieldValidationUtil.isShorterThan(
                    input.value,
                    FIELD_LENGTH_MAX_USER
                )
            ) {
                callback.formError(FormError(input.field, R.string.field_requires_fewer_chars, "usuario"))
                return false
            }
            FormField.PASSWORD,
            FormField.PASSWORD_CONFIRM -> if (fieldValidationUtil.isShorterThan(
                    input.value,
                    FIELD_LENGTH_MAX_PASSWORD
                )
            ) {
                callback.formError(FormError(input.field, R.string.field_requires_fewer_chars, "contraseña"))
                return false
            }
        }
        return true
    }

    private fun validateSpecifics(input: FormInput): Boolean {
        when (input.field) {
            FormField.EMAIL -> if (!fieldValidationUtil.validateEmail(input.value)) {
                callback.formError(FormError(input.field, R.string.field_error_invalid_email))
                return false
            }
            FormField.PASSWORD -> if (!fieldValidationUtil.validatePasswordFormat(input.value)) {
                callback.formError(
                    FormError(
                        input.field,
                        R.string.field_error_password_incorrect_format
                    )
                )
                return false
            }
            FormField.PASSWORD_CONFIRM -> if (input.value != input.compareValue) {
                callback.formError(
                    FormError(
                        input.field,
                        R.string.field_error_non_matching_password
                    )
                )
                return false
            }
        }
        return true
    }

    private fun setFieldValidated(input: FormInput, validated: Boolean) {
        fieldValidationMap[input.field] = validated
    }
}
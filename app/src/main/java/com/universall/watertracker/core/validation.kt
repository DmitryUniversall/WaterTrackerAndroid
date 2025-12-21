package com.universall.watertracker.core

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
) {
    constructor(valid: Boolean) : this(isValid = valid, error = null)
}

fun Boolean.asValidationResult(): ValidationResult =
    if (this) ValidationResult(true)
    else ValidationResult(false)


infix fun Boolean.orValidationError(message: String): ValidationResult =
    if (this) ValidationResult(true)
    else ValidationResult(false, message)


typealias Validator<T> = (T) -> ValidationResult

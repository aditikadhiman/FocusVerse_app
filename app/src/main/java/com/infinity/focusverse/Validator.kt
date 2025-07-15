package com.infinity.focusverse

object Validator {

    fun validateFirstName(fName: String): ValidationResult {
        return ValidationResult(
            status = fName.length >= 3,
            errorMessage = if (fName.length < 3) "First name too short" else null
        )
    }

    fun validateLast(lName: String): ValidationResult {
        return ValidationResult(
            status = lName.length >= 2,
            errorMessage = if (lName.length < 2) "Last name too short" else null
        )
    }

    fun validateEmail(email: String): ValidationResult {
        val isValid = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return ValidationResult(
            status = isValid,
            errorMessage = if (!isValid) "Invalid email format" else null
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            status = password.length >= 6,
            errorMessage = if (password.length < 6) "Password must be at least 6 characters" else null
        )
    }

    fun validatePrivacyPolicyAccepted(isAccepted: Boolean): ValidationResult {
        return ValidationResult(
            status = isAccepted,
            errorMessage = if (!isAccepted) "Please accept Privacy Policy" else null
        )
    }

    fun validateResetEmail(resetEmail: String): ValidationResult {
        val isValid = resetEmail.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(resetEmail).matches()
        return ValidationResult(
            status = isValid,
            errorMessage = if (!isValid) "Invalid email format" else null
        )
    }

    // ✅ NEW — used for Section/Subsection/Note title
    fun validateTitleOrName(input: String): ValidationResult {
        return ValidationResult(
            status = input.trim().length > 1,
            errorMessage = if (input.trim().length <= 1) "Title is too short" else null
        )
    }

    // ✅ NEW — used for YouTube video link
    fun validateYoutubeUrl(url: String): ValidationResult {
        val isValid = url.startsWith("http://") || url.startsWith("https://")
        return ValidationResult(
            status = isValid,
            errorMessage = if (!isValid) "Invalid YouTube URL" else null
        )
    }

    data class ValidationResult(
        val status: Boolean,
        val errorMessage: String? = null
    )
}

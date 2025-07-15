package com.infinity.focusverse.state.quote

data class QuoteUiState(
    val quoteText: String = "",
    val author: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

package com.infinity.focusverse.state.home

import com.infinity.focusverse.model.FocusReference
import com.infinity.focusverse.model.Section

data class HomeUiState(
    val focusItems: List<FocusReference> = emptyList(),
    val sections: List<Section> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
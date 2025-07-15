package com.infinity.focusverse.state.addItem

data class DialogUiState(
    val dialogType: DialogType = DialogType.NONE,
    val inputValue: String = "",
    val isValid: Boolean = false,
    val errorMessage: String? = null,
    val showDialog: Boolean = false
)


enum class DialogType {
    NONE,
    ADD_SECTION,
    ADD_SUBSECTION,
    ADD_NOTE,
    ADD_VIDEO,
    ADD_PDF,
    EDIT_SECTION,
    EDIT_SUBSECTION,
    EDIT_NOTE,
    EDIT_PDF
}

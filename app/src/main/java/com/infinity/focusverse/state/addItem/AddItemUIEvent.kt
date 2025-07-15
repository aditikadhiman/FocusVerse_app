package com.infinity.focusverse.state.addItem

sealed class DialogUiEvent {
    data class Show(val dialogType: DialogType, val initialText: String = "") : DialogUiEvent()
    data class InputChanged(val value: String) : DialogUiEvent()
    object Submit : DialogUiEvent()
    object Dismiss : DialogUiEvent()
}


//fun onEvent(event: DialogUiEvent) {
//    when (event) {
//        is DialogUiEvent.Show -> {
//            _uiState.value = DialogUiState(
//                dialogType = event.dialogType,
//                inputValue = event.initialText,
//                showDialog = true,
//                isValid = event.initialText.isNotBlank(),
//                errorMessage = null
//            )
//        }
//
//        is DialogUiEvent.InputChanged -> {
//            val type = _uiState.value.dialogType
//            val value = event.value
//            val result = when (type) {
//                DialogType.ADD_VIDEO -> Validator.validateYoutubeUrl(value)
//                DialogType.ADD_SECTION, DialogType.ADD_SUBSECTION,
//                DialogType.ADD_NOTE, DialogType.ADD_PDF,
//                DialogType.EDIT_SECTION, DialogType.EDIT_SUBSECTION,
//                DialogType.EDIT_NOTE, DialogType.EDIT_PDF -> Validator.validateTitleOrName(value)
//                else -> Validator.ValidationResult(false, "Unknown dialog type")
//            }
//
//            _uiState.value = _uiState.value.copy(
//                inputValue = value,
//                isValid = result.status,
//                errorMessage = result.errorMessage
//            )
//        }
//
//        DialogUiEvent.Submit -> {
//            if (_uiState.value.isValid) {
//                // Trigger save/edit logic here based on dialogType
//                when (_uiState.value.dialogType) {
//                    DialogType.ADD_SECTION -> addSection(_uiState.value.inputValue)
//                    DialogType.EDIT_NOTE -> updateNote(_uiState.value.inputValue)
//                    DialogType.ADD_VIDEO -> addVideo(_uiState.value.inputValue)
//                    // add other cases...
//                    else -> {}
//                }
//                _uiState.value = DialogUiState() // Reset dialog
//            }
//        }
//
//        DialogUiEvent.Dismiss -> {
//            _uiState.value = DialogUiState() // Reset dialog
//        }
//    }
//}
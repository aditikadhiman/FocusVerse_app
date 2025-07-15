package com.infinity.focusverse.state.quote.section


sealed class SectionUiEvent {
    data class LoadSection(val sectionId: String) : SectionUiEvent()
    object RefreshContent : SectionUiEvent()
    data class ShowError(val message: String) : SectionUiEvent()
}

//fun onEvent(event: SectionUiEvent) {
//    when (event) {
//        is SectionUiEvent.LoadSection -> loadSection(event.sectionId)
//        SectionUiEvent.RefreshContent -> reloadSectionData()
//        is SectionUiEvent.ShowError -> _uiState.value = _uiState.value.copy(errorMessage = event.message)
//    }
//}
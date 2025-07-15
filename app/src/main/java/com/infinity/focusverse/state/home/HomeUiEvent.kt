package com.infinity.focusverse.state.home

sealed class HomeUiEvent {
    object LoadInitialData : HomeUiEvent()
    object RefreshFocusItems : HomeUiEvent()
    object RefreshSections : HomeUiEvent()
    data class ShowError(val message: String) : HomeUiEvent()
}


//fun onEvent(event: HomeUiEvent) {
//    when (event) {
//        HomeUiEvent.LoadInitialData -> loadHomeData()
//        HomeUiEvent.RefreshFocusItems -> fetchFocusItems()
//        HomeUiEvent.RefreshSections -> fetchSections()
//        is HomeUiEvent.ShowError -> _uiState.value = _uiState.value.copy(errorMessage = event.message)
//    }
//}
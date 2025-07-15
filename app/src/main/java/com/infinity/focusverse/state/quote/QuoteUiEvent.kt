package com.infinity.focusverse.state.quote


sealed class QuoteUiEvent {
    object LoadQuote : QuoteUiEvent()
    object Retry : QuoteUiEvent()
}


//fun onEvent(event: QuoteUiEvent) {
//    when (event) {
//        QuoteUiEvent.LoadQuote, QuoteUiEvent.Retry -> fetchQuote()
//    }
//}
package com.infinity.focusverse.model

sealed class FocusUiItem {
    data class Video(val item: VideoItem) : FocusUiItem()
    data class Pdf(val item: PdfItem) : FocusUiItem()
    data class Note(val item: NoteItem) : FocusUiItem()
}

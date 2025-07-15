package com.infinity.focusverse.utils

import com.infinity.focusverse.model.*

fun FocusReference.toFocusUiItem(
    videos: List<VideoItem>,
    pdfs: List<PdfItem>,
    notes: List<NoteItem>
): FocusUiItem? {
    return when (type) {
        "video" -> videos.find { it.id == itemId }?.let { FocusUiItem.Video(it) }
        "pdf" -> pdfs.find { it.id == itemId }?.let { FocusUiItem.Pdf(it) }
        "note" -> notes.find { it.id == itemId }?.let { FocusUiItem.Note(it) }
        else -> null
    }
}

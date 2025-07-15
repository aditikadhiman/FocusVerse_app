package com.infinity.focusverse.state.quote.section

import com.infinity.focusverse.model.NoteItem
import com.infinity.focusverse.model.PdfItem
import com.infinity.focusverse.model.Subsection
import com.infinity.focusverse.model.VideoItem

data class SectionUiState(
    val sectionId: String = "",
    val sectionTitle: String = "",
    val subsections: List<Subsection> = emptyList(),
    val videos: List<VideoItem> = emptyList(),
    val notes: List<NoteItem> = emptyList(),
    val pdfs: List<PdfItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

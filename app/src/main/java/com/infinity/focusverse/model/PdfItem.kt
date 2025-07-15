package com.infinity.focusverse.model

data class PdfItem(
    val id: String = "",
    val title: String = "",
    val pdfUrl: String = "",
    val isCompleted: Boolean = false,
    val sectionId: String = "",
    val subSectionId: String = ""
)

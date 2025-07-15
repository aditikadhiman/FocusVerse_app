package com.infinity.focusverse.Repository

import com.infinity.focusverse.model.*

interface SectionRepository {

    // 🔹 Load a section by ID
    suspend fun getSectionById(sectionId: String): Section?

    // 🔹 Subsections
    suspend fun getAllSubSections(sectionId: String): List<Subsection>
    suspend fun updateSubSectionName(sectionId: String, subSectionId: String, newName: String)
    suspend fun deleteSubSection(sectionId: String, subSectionId: String)

    // 🔹 Videos
    suspend fun getAllVideos(sectionId: String): List<VideoItem>
    suspend fun updateVideo(videoItem: VideoItem)
    suspend fun deleteVideo(sectionId: String, videoId: String)

    // 🔹 PDFs
    suspend fun getAllPdfs(sectionId: String): List<PdfItem>
    suspend fun updatePdfTitle(sectionId: String, pdfId: String, newTitle: String)
    suspend fun deletePdf(sectionId: String, pdfId: String)

    // 🔹 Notes
    suspend fun getAllNotes(sectionId: String): List<NoteItem>
    suspend fun updateNoteContent(sectionId: String, noteId: String, newContent: String)
    suspend fun deleteNote(sectionId: String, noteId: String)

    // 🔹 Add to Today's Focus (focusItems)
    suspend fun addToTodayFocus(focusReference: FocusReference)
}

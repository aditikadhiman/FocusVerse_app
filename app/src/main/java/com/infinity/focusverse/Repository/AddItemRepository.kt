package com.infinity.focusverse.Repository

import android.net.Uri
import com.infinity.focusverse.model.NoteItem
import com.infinity.focusverse.model.PdfItem

interface AddItemRepository {
    suspend fun addNote(note: NoteItem)
    suspend fun addPdf(pdfItem: PdfItem, fileUri: Uri)
    suspend fun addVideo(videoId: String, sectionId: String, subSectionId: String = "")
    suspend fun addPlaylist(playlistId: String, sectionId: String, subSectionId: String = "")
    suspend fun addSubsection(sectionId: String, subsectionId: String, title: String)

}
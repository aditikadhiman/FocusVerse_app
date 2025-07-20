package com.infinity.focusverse.Repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.infinity.focusverse.model.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SectionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : SectionRepository {

    private val userId: String
        get() = auth.currentUser?.uid ?: throw Exception("User not authenticated")

    // 🔹 SECTION
    override suspend fun getSectionById(sectionId: String): Section? {
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .get()
            .await()
        return snapshot.toObject(Section::class.java)
    }

    // 🔹 SUBSECTIONS
    override suspend fun getAllSubSections(sectionId: String): List<Subsection> {
        return firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("subsections")
            .get()
            .await()
            .toObjects(Subsection::class.java)
    }

    override suspend fun updateSubSectionName(sectionId: String, subSectionId: String, newName: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("subsections")
            .document(subSectionId)
            .update("subsectionName", newName)
            .await()
    }

    override suspend fun deleteSubSection(sectionId: String, subSectionId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("subsections")
            .document(subSectionId)
            .delete()
            .await()
    }

    // 🔹 VIDEOS
    override suspend fun getAllVideos(sectionId: String): List<VideoItem> {
        return firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("videos")
            .get()
            .await()
            .toObjects(VideoItem::class.java)
    }

    override suspend fun updateVideo(videoItem: VideoItem) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(videoItem.sectionId)
            .collection("videos")
            .document(videoItem.id)
            .set(videoItem)
            .await()
    }

    override suspend fun deleteVideo(sectionId: String, videoId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("videos")
            .document(videoId)
            .delete()
            .await()
    }

    // 🔹 PDFS
    override suspend fun getAllPdfs(sectionId: String): List<PdfItem> {
        return firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("pdfs")
            .get()
            .await()
            .toObjects(PdfItem::class.java)
    }

    override suspend fun updatePdfTitle(sectionId: String, pdfId: String, newTitle: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("pdfs")
            .document(pdfId)
            .update("title", newTitle)
            .await()
    }

    override suspend fun deletePdf(sectionId: String, pdfId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("pdfs")
            .document(pdfId)
            .delete()
            .await()
    }

    // 🔹 NOTES
    override suspend fun getAllNotes(sectionId: String): List<NoteItem> {
        return firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("notes")
            .get()
            .await()
            .toObjects(NoteItem::class.java)
    }

    override suspend fun updateNoteContent(sectionId: String, noteId: String, newContent: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("notes")
            .document(noteId)
            .update("content", newContent)
            .await()
    }

    override suspend fun deleteNote(sectionId: String, noteId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("notes")
            .document(noteId)
            .delete()
            .await()
    }

    // 🔹 TODAY'S FOCUS
    override suspend fun addToTodayFocus(focusReference: FocusReference) {
        firestore.collection("users")
            .document(userId)
            .collection("focusItems")
            .document(focusReference.itemId)
            .set(focusReference)
            .await()
    }

//    override suspend fun updateNoteCompletion(sectionId: String, noteId: String, isCompleted: Boolean) {
//        firestore.collection("users")
//            .document(userId)
//            .collection("sections")
//            .document(sectionId)
//            .collection("notes")
//            .document(noteId)
//            .update("isCompleted", isCompleted)
//            .await()
//    }

//    override suspend fun updateVideoCompletion(sectionId: String, videoId: String, isCompleted: Boolean) {
//        firestore.collection("users")
//            .document(userId)
//            .collection("sections")
//            .document(sectionId)
//            .collection("videos")
//            .document(videoId)
//            .update("isCompleted", isCompleted)
//            .await()
//    }

//    override suspend fun updatePdfCompletion(sectionId: String, pdfId: String, isCompleted: Boolean) {
//        firestore.collection("users")
//            .document(userId)
//            .collection("sections")
//            .document(sectionId)
//            .collection("pdfs")
//            .document(pdfId)
//            .update("isCompleted", isCompleted)
//            .await()
//    }
    override suspend fun updateVideoCompletion(sectionId: String, videoId: String, isCompleted: Boolean) {
        firestore.collection("sections")
            .document(sectionId)
            .collection("videos")
            .document(videoId)
            .update("isCompleted", isCompleted)
    }

    override suspend fun updatePdfCompletion(sectionId: String, pdfId: String, isCompleted: Boolean) {
        firestore.collection("sections")
            .document(sectionId)
            .collection("pdfs")
            .document(pdfId)
            .update("isCompleted", isCompleted)
    }

    override suspend fun updateNoteCompletion(sectionId: String, noteId: String, isCompleted: Boolean) {
        firestore.collection("sections")
            .document(sectionId)
            .collection("notes")
            .document(noteId)
            .update("isCompleted", isCompleted)
    }

    // Your existing getSectionData(), etc.
}

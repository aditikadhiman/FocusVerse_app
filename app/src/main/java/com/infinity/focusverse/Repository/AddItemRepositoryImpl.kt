package com.infinity.focusverse.Repository//package com.infinity.focusverse.Repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.infinity.focusverse.model.NoteItem
import com.infinity.focusverse.model.PdfItem
import com.infinity.focusverse.model.VideoItem
import com.infinity.focusverse.utils.YouTubeApiHandler
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AddItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val youTubeApiHandler: YouTubeApiHandler
) : AddItemRepository {

    override suspend fun addNote(note: NoteItem) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        val noteRef = getItemRef(userId, note.sectionId, note.subSectionId, "notes")
        noteRef.document(note.id).set(note).await()
    }

    override suspend fun addPdf(pdfItem: PdfItem, fileUri: Uri) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        val pdfRef = getItemRef(userId, pdfItem.sectionId, pdfItem.subSectionId, "pdfs")
        pdfRef.document(pdfItem.id).set(pdfItem).await()
    }

    override suspend fun addVideo(videoId: String, sectionId: String, subSectionId: String) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        val videoData = youTubeApiHandler.fetchSingleVideo(videoId)

        val videoItem = VideoItem(
            id = videoData.videoId,
            title = videoData.title,
            channel = videoData.channelName,
            duration = videoData.duration,
            thumbnailUrl = videoData.thumbnail,
            isCompleted = false,
            sectionId = sectionId,
            subSectionId = subSectionId,
            videoUrl = "https://www.youtube.com/watch?v=${videoData.videoId}"
        )

        val videoRef = getItemRef(userId, sectionId, subSectionId, "videos")
        videoRef.document(videoItem.id).set(videoItem).await()
    }

//    override suspend fun addPlaylist(playlistId: String, sectionId: String, subSectionId: String) {
//        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
//        val videos = youTubeApiHandler.fetchPlaylistVideos(playlistId)
//        val videoRef = getItemRef(userId, sectionId, subSectionId, "videos")
//
//        videos.forEach { videoData ->
//            val videoItem = VideoItem(
//                id = videoData.videoId,
//                title = videoData.title,
//                channel = videoData.channelName,
//                duration = videoData.duration,
//                thumbnailUrl = videoData.thumbnail,
//                isCompleted = false,
//                sectionId = sectionId,
//                subSectionId = subSectionId,
//                videoUrl = "https://www.youtube.com/watch?v=${videoData.videoId}"
//            )
//            videoRef.document(videoItem.id).set(videoItem).await()
//        }
//    }
//override suspend fun addPlaylist(playlistId: String, sectionId: String, subSectionId: String) {
//    val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
//    val videoRef = getItemRef(userId, sectionId, subSectionId, "videos")
//
//    var nextPageToken: String? = null
//
//    do {
//        val response = youTubeApiHandler.fetchPlaylistVideos(playlistId, nextPageToken)
//
//        response.videos.forEach { videoData ->
//            val videoItem = VideoItem(
//                id = videoData.videoId,
//                title = videoData.title,
//                channel = videoData.channelName,
//                duration = videoData.duration,
//                thumbnailUrl = videoData.thumbnail,
//                isCompleted = false,
//                sectionId = sectionId,
//                subSectionId = subSectionId,
//                videoUrl = "https://www.youtube.com/watch?v=${videoData.videoId}"
//            )
//            videoRef.document(videoItem.id).set(videoItem).await()
//        }
//
//        nextPageToken = response.nextPageToken
//
//    } while (nextPageToken != null)
//}
override suspend fun addPlaylist(
    playlistId: String,
    sectionId: String,
    subSectionId: String
) {
    val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
    val videoRef = getItemRef(userId, sectionId, subSectionId, "videos")

    var nextPageToken: String? = null

    try {
        do {
            val response = youTubeApiHandler.fetchPlaylistVideos(playlistId)

            response.forEach { videoData ->
                val videoItem = VideoItem(
                    id = videoData.videoId,
                    title = videoData.title,
                    channel = videoData.channelName,
                    duration = videoData.duration,
                    thumbnailUrl = videoData.thumbnail,
                    isCompleted = false,
                    sectionId = sectionId,
                    subSectionId = subSectionId,
                    videoUrl = "https://www.youtube.com/watch?v=${videoData.videoId}"
                )
                videoRef.document(videoItem.id).set(videoItem).await()
            }

            nextPageToken = nextPageToken

        } while (!nextPageToken.isNullOrEmpty())
    } catch (e: Exception) {
        // You can log or propagate the error as needed
        throw Exception("Failed to fetch playlist: ${e.message}", e)
    }
}

    private fun getItemRef(userId: String, sectionId: String, subSectionId: String, collection: String) =
        if (subSectionId.isEmpty()) {
            firestore.collection("users")
                .document(userId)
                .collection("sections")
                .document(sectionId)
                .collection(collection)
        } else {
            firestore.collection("users")
                .document(userId)
                .collection("sections")
                .document(sectionId)
                .collection("subsections")
                .document(subSectionId)
                .collection(collection)
        }

    override suspend fun addSubsection(sectionId: String, subsectionId: String, title: String) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

        val subSection = hashMapOf(
            "subSection_id" to subsectionId,
            "title" to title,
            "sectionId" to sectionId
        )

        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("subsections")
            .document(subsectionId)
            .set(subSection)
            .await()
    }

}

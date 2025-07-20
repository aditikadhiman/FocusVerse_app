package com.infinity.focusverse.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.infinity.focusverse.Repository.SectionRepository
import com.infinity.focusverse.model.FocusReference
import com.infinity.focusverse.model.NoteItem
import com.infinity.focusverse.model.PdfItem
import com.infinity.focusverse.model.VideoItem
import com.infinity.focusverse.state.SectionUiState
import com.infinity.focusverse.state.addItem.DialogType
import com.infinity.focusverse.state.addItem.DialogUiEvent
import com.infinity.focusverse.state.addItem.DialogUiState
import com.infinity.focusverse.utils.FirebaseUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionViewModel @Inject constructor(
    private val repository: SectionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SectionUiState())
    val uiState: StateFlow<SectionUiState> = _uiState.asStateFlow()

    private val _dialogState = MutableStateFlow(DialogUiState())
    val dialogState: StateFlow<DialogUiState> = _dialogState.asStateFlow()

    private var editingItemId: String? = null
    private var sectionId: String = ""

    fun loadSection(sectionId: String) {
        this.sectionId = sectionId
        viewModelScope.launch {
            try {
                val section = repository.getSectionById(sectionId)
                val videos = repository.getAllVideos(sectionId)
                val pdfs = repository.getAllPdfs(sectionId)
                val notes = repository.getAllNotes(sectionId)
                val subSections = repository.getAllSubSections(sectionId)

                _uiState.update {
                    it.copy(
                        section = section,
                        videos = videos,
                        pdfs = pdfs,
                        notes = notes,
                        subSections = subSections
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    // ------------------ Dialog Event Handling ------------------
    fun onDialogEvent(event: DialogUiEvent) {
        when (event) {
            is DialogUiEvent.Show -> {
                _dialogState.value = DialogUiState(
                    dialogType = event.dialogType,
                    inputValue = event.initialText,
                    isValid = event.initialText.isNotBlank(),
                    showDialog = true
                )
            }

            is DialogUiEvent.InputChanged -> {
                val isValid = event.value.isNotBlank()
                _dialogState.update {
                    it.copy(
                        inputValue = event.value,
                        isValid = isValid,
                        errorMessage = if (!isValid) "This field cannot be empty" else null
                    )
                }
            }

            is DialogUiEvent.Submit -> {
                val input = _dialogState.value.inputValue
                when (_dialogState.value.dialogType) {
                    DialogType.EDIT_SUBSECTION -> {
                        editingItemId?.let {
                            updateSubSectionName(it, input)
                        }
                    }

                    DialogType.EDIT_PDF -> {
                        editingItemId?.let {
                            updatePdfTitle(it, input)
                        }
                    }

                    DialogType.EDIT_NOTE -> {
                        editingItemId?.let {
                            updateNoteContent(it, input)
                        }
                    }

                    else -> Unit
                }

                _dialogState.value = DialogUiState()
                editingItemId = null
            }

            is DialogUiEvent.Dismiss -> {
                _dialogState.value = DialogUiState()
                editingItemId = null
            }
        }
    }

    fun startEditing(dialogType: DialogType, itemId: String, initialText: String) {
        editingItemId = itemId
        onDialogEvent(DialogUiEvent.Show(dialogType, initialText))
    }

    // ------------------ Update Functions ------------------

    private fun updateSubSectionName(subSectionId: String, newName: String) {
        viewModelScope.launch {
            repository.updateSubSectionName(sectionId, subSectionId, newName)
            loadSection(sectionId)
        }
    }

    private fun updatePdfTitle(pdfId: String, newTitle: String) {
        viewModelScope.launch {
            repository.updatePdfTitle(sectionId, pdfId, newTitle)
            loadSection(sectionId)
        }
    }

    private fun updateNoteContent(noteId: String, newContent: String) {
        viewModelScope.launch {
            repository.updateNoteContent(sectionId, noteId, newContent)
            loadSection(sectionId)
        }
    }

    // ------------------ Delete Functions ------------------

    fun deleteVideo(videoId: String) {
        viewModelScope.launch {
            repository.deleteVideo(sectionId, videoId)
            loadSection(sectionId)
        }
    }

    fun deletePdf(pdfId: String) {
        viewModelScope.launch {
            repository.deletePdf(sectionId, pdfId)
            loadSection(sectionId)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteNote(sectionId, noteId)
            loadSection(sectionId)
        }
    }

    fun deleteSubSection(subSectionId: String) {
        viewModelScope.launch {
            repository.deleteSubSection(sectionId, subSectionId)
            loadSection(sectionId)
        }
    }

    // ------------------ Add to Today's Focus ------------------

    fun addToTodayFocus(reference: FocusReference) {
        viewModelScope.launch {
            repository.addToTodayFocus(reference)
        }
    }


    fun toggleVideoCompletion(sectionId: String, video: VideoItem) {
        val newValue = !video.isCompleted
        viewModelScope.launch {
            repository.updateVideoCompletion(sectionId, video.id, newValue)
            updateVideoCompletionLocally(video.id, newValue)
        }
    }

    private fun updateVideoCompletionLocally(videoId: String, isCompleted: Boolean) {
        _uiState.update { current ->
            current.copy(
                videos = current.videos.map {
                    if (it.id == videoId) it.copy(isCompleted = isCompleted) else it
                }
            )
        }
    }

    fun togglePdfCompletion(sectionId: String, pdf: PdfItem) {
        val newValue = !pdf.isCompleted
        viewModelScope.launch {
            repository.updatePdfCompletion(sectionId, pdf.id, newValue)
            updatePdfCompletionLocally(pdf.id, newValue)
        }
    }

    private fun updatePdfCompletionLocally(pdfId: String, isCompleted: Boolean) {
        _uiState.update { current ->
            current.copy(
                pdfs = current.pdfs.map {
                    if (it.id == pdfId) it.copy(isCompleted = isCompleted) else it
                }
            )
        }
    }

    fun toggleNoteCompletion(sectionId: String, note: NoteItem) {
        val newValue = !note.isCompleted
        viewModelScope.launch {
            try {
                repository.updateNoteCompletion(sectionId, note.id, newValue)
                updateNoteCompletionLocally(note.id, newValue)
            } catch (e: Exception) {
                // Optional: Handle error or show a snackbar
            }
        }
    }


    private fun updateNoteCompletionLocally(noteId: String, isCompleted: Boolean) {
        _uiState.update { current ->
            current.copy(
                notes = current.notes.map {
                    if (it.id == noteId) it.copy(isCompleted = isCompleted) else it
                }
            )
        }
    }

}

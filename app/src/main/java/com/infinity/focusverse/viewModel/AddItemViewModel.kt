package com.infinity.focusverse.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.focusverse.Repository.AddItemRepository
import com.infinity.focusverse.model.NoteItem
import com.infinity.focusverse.model.PdfItem
import com.infinity.focusverse.state.AddItemEvent
import com.infinity.focusverse.state.addItem.DialogType
import com.infinity.focusverse.state.addItem.DialogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repo: AddItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DialogUiState())
    val uiState: StateFlow<DialogUiState> = _uiState

    fun onEvent(event: AddItemEvent) {
        when (event) {
            is AddItemEvent.ShowDialog-> {
                _uiState.value = _uiState.value.copy(
                    dialogType = event.dialogType,
                    showDialog = true,
                    inputValue = "",
                    errorMessage = null,
                    isValid = false
                )
            }
            is AddItemEvent.InputChanged -> {
                val isValid = event.value.isNotBlank()
                _uiState.value = _uiState.value.copy(
                    inputValue = event.value,
                    isValid = isValid,
                    errorMessage = if (isValid) null else "Field can't be empty"
                )
            }
            is AddItemEvent.Dismiss -> {
                _uiState.value = DialogUiState()
            }
            is AddItemEvent.Submit -> {
                handleSubmit(event.sectionId, event.subSectionId)
            }
        }
    }

    private fun handleSubmit(sectionId: String, subSectionId: String) = viewModelScope.launch {
        val input = _uiState.value.inputValue.trim()
        val id = UUID.randomUUID().toString()

        try {
            when (_uiState.value.dialogType) {
                DialogType.ADD_NOTE -> {
                    repo.addNote(NoteItem(id = id, title = input, sectionId = sectionId, subSectionId = subSectionId))
                }
                DialogType.ADD_PDF -> {
                    // Dummy PDF URL for now (replace with actual upload logic if needed)
                    repo.addPdf(PdfItem(id = id, title = input, pdfUrl = "https://firebasestorage.com/sample.pdf",
                        sectionId = sectionId, subSectionId = subSectionId), fileUri = Uri.EMPTY)
                }
                DialogType.ADD_VIDEO -> {
                    if (input.startsWith("PL")) {
                        repo.addPlaylist(input, sectionId, subSectionId)
                    } else {
                        repo.addVideo(input, sectionId, subSectionId)
                    }
                }
                DialogType.ADD_SUBSECTION -> {
                    repo.addSubsection(sectionId, id, input)
                }

                else -> {}
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
        } finally {
            _uiState.value = DialogUiState() // reset state
        }
    }
}

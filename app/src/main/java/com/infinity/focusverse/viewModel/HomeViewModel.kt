package com.infinity.focusverse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.infinity.focusverse.Repository.HomeRepository
import com.infinity.focusverse.api.model.QuoteResponse
import com.infinity.focusverse.model.FocusReference
import com.infinity.focusverse.model.FocusUiItem
import com.infinity.focusverse.model.Section
import com.infinity.focusverse.state.addItem.DialogType
import com.infinity.focusverse.state.addItem.DialogUiEvent
import com.infinity.focusverse.state.addItem.DialogUiState
import com.infinity.focusverse.state.home.HomeUiEvent
import com.infinity.focusverse.state.home.HomeUiState
import com.infinity.focusverse.state.quote.QuoteUiEvent
import com.infinity.focusverse.state.quote.QuoteUiState
import com.infinity.focusverse.utils.toFocusUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    // --- Home UI State ---
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // --- Quote UI State ---
    private val _quoteState = MutableStateFlow(QuoteUiState())
    val quoteState: StateFlow<QuoteUiState> = _quoteState.asStateFlow()

    // --- Dialog State ---
    private val _dialogState = MutableStateFlow(DialogUiState())
    val dialogState: StateFlow<DialogUiState> = _dialogState.asStateFlow()

    // --- Editing section tracker ---
    private var editingSectionId: String? = null

    private val _focusUiItems = MutableStateFlow<List<FocusUiItem>>(emptyList())
    val focusUiItems: StateFlow<List<FocusUiItem>> = _focusUiItems.asStateFlow()


    init {
        loadInitialData()
    }

    // -----------------------------------------------------
    // --- HOME UI EVENT HANDLING ---
    fun onHomeEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.LoadInitialData -> loadInitialData()
            is HomeUiEvent.RefreshFocusItems -> fetchFocusItems()
            is HomeUiEvent.RefreshSections -> fetchSections()
            is HomeUiEvent.ShowError -> _uiState.update { it.copy(errorMessage = event.message) }
            else -> {}
        }
    }

    // -----------------------------------------------------
    // --- QUOTE EVENT HANDLING ---
    fun onQuoteEvent(event: QuoteUiEvent) {
        when (event) {
            QuoteUiEvent.LoadQuote -> fetchQuote()
            QuoteUiEvent.Retry -> fetchQuote()
        }
    }

    // -----------------------------------------------------
    // --- DIALOG EVENT HANDLING ---
    fun onDialogEvent(event: DialogUiEvent) {
        when (event) {
            is DialogUiEvent.Show -> {
                _dialogState.value = DialogUiState(
                    dialogType = event.dialogType,
                    inputValue = event.initialText,
                    isValid = event.initialText.isNotBlank(),
                    showDialog = true
                )

                // ✅ Set editing ID directly instead of matching by name
//                editingSectionId = event.editingId

//                 Track editing ID for edit section case
                if (event.dialogType == DialogType.EDIT_SECTION) {
                    editingSectionId = _uiState.value.sections.find {
                        it.sectionName == event.initialText
                    }?.section_id
                }
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

            is DialogUiEvent.Dismiss -> {
                _dialogState.value = DialogUiState()
                editingSectionId = null
            }

            is DialogUiEvent.Submit -> {
                val input = _dialogState.value.inputValue

                when (_dialogState.value.dialogType) {
                    DialogType.ADD_SECTION -> {
                        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
                        val newSection = Section(
                            section_id = UUID.randomUUID().toString(),
                            sectionName = input,
                            userId = userId
                        )
                        viewModelScope.launch {
                            repository.addSection(newSection)
                            fetchSections()
                        }
                    }

                    DialogType.EDIT_SECTION -> {
                        editingSectionId?.let { id ->
                            viewModelScope.launch {
                                repository.updateSectionTitle(id, input)
                                fetchSections()
                            }
                        }
                    }

                    else -> {}
                }

                _dialogState.value = DialogUiState()
                editingSectionId = null
            }
        }
    }

    // -----------------------------------------------------
//    // --- FIRESTORE LOGIC (Focus Items) ---
//    fun updateFocusItem(item: FocusReference) {
//        viewModelScope.launch {
//            repository.updateFocusItem(item)
//            fetchFocusItems()
//        }
//    }

    fun updateFocusItem(item: FocusReference) {
        viewModelScope.launch {
            try {
                // 1. Update Firestore
                repository.updateFocusItem(item)

                // 2. Update in-memory UI list to trigger recomposition
                _focusUiItems.update { currentList ->
                    currentList.map { uiItem ->
                        when (uiItem) {
                            is FocusUiItem.Note -> {
                                if (uiItem.item.id == item.itemId) {
                                    uiItem.copy(item = uiItem.item.copy(isCompleted = item.isCompleted))
                                } else uiItem
                            }

                            is FocusUiItem.Pdf -> {
                                if (uiItem.item.id == item.itemId) {
                                    uiItem.copy(item = uiItem.item.copy(isCompleted = item.isCompleted))
                                } else uiItem
                            }

                            is FocusUiItem.Video -> {
                                if (uiItem.item.id == item.itemId) {
                                    uiItem.copy(item = uiItem.item.copy(isCompleted = item.isCompleted))
                                } else uiItem
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Update failed: ${e.message}")
                }
            }
        }
    }

    fun removeFocusItem(itemId: String) {
        viewModelScope.launch {
            repository.removeFocusItem(itemId)
            fetchFocusItems()
        }
    }

    fun removeAllItems() {
        viewModelScope.launch {
            repository.removeAllFocusItems()
            fetchFocusItems()
        }
    }

    fun checkAllItems(status: Boolean) {
        viewModelScope.launch {
            repository.updateAllFocusItemsStatus(status)
            fetchFocusItems()
        }
    }

    fun deleteSection(sectionId: String) {
        viewModelScope.launch {
            repository.deleteSection(sectionId)
            fetchSections()
        }
    }

    fun updateSectionTitle(sectionId: String) {
        val newTitle = _dialogState.value.inputValue
        viewModelScope.launch {
            repository.updateSectionTitle(sectionId, newTitle)
            fetchSections()
        }
    }

    // -----------------------------------------------------
    // --- PRIVATE LOADERS ---
    private fun loadInitialData() {
        fetchFocusItems()
        fetchSections()
        fetchQuote()
    }

    private fun fetchFocusItems() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val references = repository.getAllFocusItems()
                val sections = repository.getAllSections()

                // ✅ Fetch all actual items from subcollections
                val allVideos = repository.getAllVideos()
                val allPdfs = repository.getAllPdfs()
                val allNotes = repository.getAllNotes()

                val uiItems = references.mapNotNull {
                    it.toFocusUiItem(
                        videos = allVideos,
                        pdfs = allPdfs,
                        notes = allNotes
                    )
                }

                _uiState.update {
                    it.copy(
                        focusItems = references,
                        sections = sections,
                        isLoading = false,
                        errorMessage = null
                    )
                }

                _focusUiItems.value = uiItems

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }


    private fun fetchSections() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val sections = repository.getAllSections()
                _uiState.update {
                    it.copy(
                        sections = sections,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private fun fetchQuote() {
        viewModelScope.launch {
            try {
                _quoteState.value = _quoteState.value.copy(isLoading = true)
                val quote: QuoteResponse = repository.getRandomQuote()
                _quoteState.value = _quoteState.value.copy(
                    quoteText = quote.q,
                    author = quote.a,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _quoteState.value = _quoteState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

}

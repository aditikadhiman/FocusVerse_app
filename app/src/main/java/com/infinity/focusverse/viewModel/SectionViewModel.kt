package com.infinity.focusverse.viewModel

//import com.infinity.focusverse.Repository.SectionRepository

//@HiltViewModel
//class SectionViewModel @Inject constructor(
//    private val repository: SectionRepository
//) : ViewModel() {
//
//    var sectionUiState by mutableStateOf<Section?>(null)
//        private set
//
//    var videoList by mutableStateOf<List<VideoItem>>(emptyList())
//        private set
//
//    var noteList by mutableStateOf<List<NoteItem>>(emptyList())
//        private set
//
//    var pdfList by mutableStateOf<List<PdfItem>>(emptyList())
//        private set
//
//    var subsectionList by mutableStateOf<List<Subsection>>(emptyList())
//        private set
//
//    fun loadSection(sectionId: String, userId: String) {
//        viewModelScope.launch {
//            try {
//                sectionUiState = repository.getSection(sectionId, userId)
//                videoList = repository.getVideos(sectionId, userId)
//                noteList = repository.getNotes(sectionId, userId)
//                pdfList = repository.getPdfs(sectionId, userId)
//                subsectionList = repository.getSubsections(sectionId, userId)
//            } catch (e: Exception) {
//                Log.e("SectionViewModel", "Error loading section: ${e.message}")
//            }
//        }
//    }
//}

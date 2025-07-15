package com.infinity.focusverse.uifiles.Screens.auth

//import com.infinity.focusverse.viewModel.SectionViewModel

//
//@Composable
//fun SectionScreen(
//    sectionId: String,
//    userId: String,
//    viewModel: SectionViewModel = hiltViewModel(),
//    onAddItemClick: () -> Unit,
//    navController: NavHostController
//) {
//    val section = viewModel.sectionUiState
//    val videos = viewModel.videoList
//    val pdfs = viewModel.pdfList
//    val notes = viewModel.noteList
//
//    val completedCount = remember(videos, pdfs, notes) {
//        videos.count { it.isCompleted } +
//                pdfs.count { it.isCompleted } +
//                notes.count { it.isCompleted }
//    }
//    val totalCount = videos.size + pdfs.size + notes.size
//
//    LaunchedEffect(Unit) {
//        viewModel.loadSection(sectionId, userId)
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 72.dp) // Leave space for FAB
//        ) {
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = section?.sectionName ?: "Loading...",
//                        style = MaterialTheme.typography.h1.copy(color = Color.White)
//                    )
//                    Text(
//                        text = "$completedCount/$totalCount",
//                        style = MaterialTheme.typography.h1.copy(color = Color.White)
//                    )
//                }
//            }
//
//            // Video List
//            items(videos) { video ->
//                VideoComponent2(
//                    videoItem = video,
//                    onMenuClick = { /* Show menu actions */ },
//                    onCheckToggle = { viewModel.toggleVideoCompleted(video) }
//                )
//            }
//
//            // PDF Row
//            item {
//                LazyRow(modifier = Modifier.padding(8.dp)) {
//                    items(pdfs) { pdf ->
//                        PdfComponent2(
//                            pdfItem = pdf,
//                            onMenuClick = { /* Show menu actions */ },
//                            onCheckToggle = { viewModel.togglePdfCompleted(pdf) }
//                        )
//                    }
//                }
//            }
//
//            // Notes
//            items(notes) { note ->
//                NoteComponent2(
//                    noteItem = note,
//                    onMenuClick = { /* Show menu actions */ },
//                    onCheckToggle = { viewModel.toggleNoteCompleted(note) }
//                )
//            }
//
//            item {
//                Spacer(modifier = Modifier.height(80.dp))
//            }
//        }
//
//        // Floating Action Button
//        FloatingActionButton(
//            onClick = onAddItemClick,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(16.dp),
//            containerColor = Color.White
//        ) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item", tint = Color.Black)
//        }
//    }
//}

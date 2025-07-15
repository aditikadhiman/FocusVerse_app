package com.infinity.focusverse.uifiles.Screens.auth

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.infinity.focusverse.R
import com.infinity.focusverse.model.FocusReference
import com.infinity.focusverse.navigation.Screen
import com.infinity.focusverse.state.addItem.DialogType
import com.infinity.focusverse.state.addItem.DialogUiEvent
import com.infinity.focusverse.ui.theme.background
import com.infinity.focusverse.uifiles.Components.*
import com.infinity.focusverse.utils.MenuOption
import com.infinity.focusverse.viewModel.SectionViewModel

@Composable
fun SectionScreen(
    navController: NavController,
    sectionId: String,
    viewModel: SectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()
    val context = LocalContext.current

    // Initial load
    LaunchedEffect(Unit) {
        viewModel.loadSection(sectionId)
    }

    Scaffold(
        backgroundColor = background,
        floatingActionButton = {
            AddButtonComponent (
                onClick = { navController.navigate(Screen.AddItem.passArgs(sectionId))
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Heading3(uiState.section?.sectionName ?: "")

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.subSections) { sub ->
                    SectionCardComponent(
                        sectionName = sub.title,
                        onClick = {},
                        menuItems = MenuOption.subsectionMenu.map { it.title },
                        onMenuItemClick = { selected ->
                            when (MenuOption.fromTitle(selected)) {
                                MenuOption.Edit -> viewModel.startEditing(
                                    DialogType.EDIT_SUBSECTION, sub.subSection_id, sub.title
                                )

                                MenuOption.Delete -> viewModel.deleteSubSection(sub.subSection_id)

                                MenuOption.AddToFocus -> viewModel.addToTodayFocus(
                                    FocusReference(sub.subSection_id, sectionId, "subsection", sub.title, false)
                                )

                                else -> {}
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.pdfs) { pdf ->
                    PdfComponent2(
                        pdfItem = pdf,
                        image = painterResource(id = R.drawable.pdf_icon),
                        onCheckedChange = {
                            viewModel.addToTodayFocus(
                                FocusReference(pdf.id, sectionId, "pdf", pdf.title, it.isCompleted)
                            )
                        },
                        onPdfClick = {
                            pdf.pdfUrl?.let { url ->
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setDataAndType(Uri.parse(url), "application/pdf")
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                context.startActivity(intent)
                            }
                        },
                        menuItems = MenuOption.itemMenu.map { it.title },
                        onMenuItemClick = { title ->
                            when (MenuOption.fromTitle(title)) {
                                MenuOption.Edit -> viewModel.startEditing(DialogType.EDIT_PDF, pdf.id, pdf.title)
                                MenuOption.Delete -> viewModel.deletePdf(pdf.id)
                                MenuOption.AddToFocus -> viewModel.addToTodayFocus(
                                    FocusReference(pdf.id, sectionId, "pdf", pdf.title, false)
                                )
                                else -> {}
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.videos) { video ->
                    VideoComponent2(
                        videoItem = video,
                        placeholder = painterResource(id = R.drawable.ic_youtube),
                        errorImage = painterResource(id = R.drawable.ic_youtube),
                        onCheckedChange = {
                            viewModel.addToTodayFocus(
                                FocusReference(video.id, sectionId, "video", video.title, it.isCompleted)
                            )
                        },
                        onThumbnailClick = {
                            video.videoUrl?.let { url ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                ContextCompat.startActivity(context, intent, null)
                            }
                        },
                        menuItems = MenuOption.itemMenu.map { it.title },
                        onMenuItemClick = { title ->
                            when (MenuOption.fromTitle(title)) {
                                MenuOption.Edit -> {} // not handled
                                MenuOption.Delete -> viewModel.deleteVideo(video.id)
                                MenuOption.AddToFocus -> viewModel.addToTodayFocus(
                                    FocusReference(video.id, sectionId, "video", video.title, false)
                                )
                                else -> {}
                            }
                        }
                    )
                }

                items(uiState.notes) { note ->
                    NoteComponent2(
                        noteItem = note,
                        onCheckedChange = {
                            viewModel.addToTodayFocus(
                                FocusReference(note.id, sectionId, "note", note.title, it.isCompleted)
                            )
                        },
                        onNoteClick = {},
                        menuItems = MenuOption.itemMenu.map { it.title },
                        onMenuItemClick = { title ->
                            when (MenuOption.fromTitle(title)) {
                                MenuOption.Edit -> viewModel.startEditing(DialogType.EDIT_NOTE, note.id, note.content)
                                MenuOption.Delete -> viewModel.deleteNote(note.id)
                                MenuOption.AddToFocus -> viewModel.addToTodayFocus(
                                    FocusReference(note.id, sectionId, "note", note.title, false)
                                )
                                else -> {}
                            }
                        }
                    )
                }
            }
        }

        InputDialogComponent(
            dialogTitle = when (dialogState.dialogType) {
                DialogType.EDIT_NOTE -> "Edit Note"
                DialogType.EDIT_PDF -> "Edit PDF Title"
                DialogType.EDIT_SUBSECTION -> "Edit Subsection"
                else -> ""
            },
            label = when (dialogState.dialogType) {
                DialogType.EDIT_NOTE -> "Note Content"
                else -> "Title"
            },
            initialText = dialogState.inputValue,
            showDialog = dialogState.showDialog,
            isConfirmEnabled = dialogState.isValid,
            errorMessage = dialogState.errorMessage,
            onTextChanged = { viewModel.onDialogEvent(DialogUiEvent.InputChanged(it)) },
            onDismiss = { viewModel.onDialogEvent(DialogUiEvent.Dismiss) },
            onConfirm = { viewModel.onDialogEvent(DialogUiEvent.Submit) }
        )
    }
}
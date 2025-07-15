package com.infinity.focusverse.uifiles.Screens.auth

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.infinity.focusverse.R
import com.infinity.focusverse.model.FocusReference
import com.infinity.focusverse.model.FocusUiItem
import com.infinity.focusverse.state.addItem.DialogType
import com.infinity.focusverse.state.addItem.DialogUiEvent
import com.infinity.focusverse.ui.theme.PrimaryButton
import com.infinity.focusverse.ui.theme.background
import com.infinity.focusverse.uifiles.Components.*
import com.infinity.focusverse.utils.MenuOption
import com.infinity.focusverse.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val quoteState by viewModel.quoteState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()
    val focusUiItems by viewModel.focusUiItems.collectAsState()
    val context = LocalContext.current

    Scaffold(
        backgroundColor = background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onDialogEvent(DialogUiEvent.Show(DialogType.ADD_SECTION))
                },
                backgroundColor = PrimaryButton
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Section")
            }
        },
        bottomBar = {
            BottomBarComponent(
                onClickHome = { /* Already on Home */ },
                onClickProfile = { navController.navigate("profile") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {

            Spacer(modifier = Modifier.height(2.dp))

            Heading1("F", "ocus", "V", "erse")

            Spacer(modifier = Modifier.height(15.dp))

            Heading2(
                value = "Today's Focus",
                menuItems = MenuOption.todayFocusMenu.map { it.title },
                onMenuItemClick = { selected ->
                    when (MenuOption.fromTitle(selected)) {
                        MenuOption.CheckAll -> viewModel.checkAllItems(true)
                        MenuOption.UncheckAll -> viewModel.checkAllItems(false)
                        MenuOption.RemoveAll -> viewModel.removeAllItems()
                        else -> Unit
                    }
                }
            )

            Spacer(modifier=Modifier.height(15.dp))

            ProgressComponent(value = stringResource(id = R.string.progress), value1 = uiState.focusItems.count{it.isCompleted} , value2 =uiState.focusItems.size )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(focusUiItems) { item ->
                    when (item) {
                        is FocusUiItem.Video -> {
                            VideoComponent(
                                videoItem = item.item,
                                placeholder = painterResource(id = R.drawable.ic_youtube),
                                errorImage = painterResource(id = R.drawable.ic_youtube),
                                onCheckedChange = { updated ->
                                    viewModel.updateFocusItem(
                                        FocusReference(
                                            itemId = updated.id,
                                            sectionId = updated.sectionId,
                                            type = "video",
                                            title = updated.title,
                                            isCompleted = updated.isCompleted
                                        )
                                    )
                                },
                                menuItems = MenuOption.itemMenu.map { it.title },
                                onMenuItemClick = { title ->
                                    if (MenuOption.fromTitle(title) == MenuOption.Remove)
                                        viewModel.removeFocusItem(item.item.id)
                                },
                                onThumbnailClick = {
                                    if (!item.item.videoUrl.isNullOrBlank()) {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.item.videoUrl))
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        ContextCompat.startActivity(context, intent, null)
                                    }
                                }
                            )
                        }

                        is FocusUiItem.Pdf -> {
                            PdfComponent(
                                pdfItem = item.item,
                                image = painterResource(id = R.drawable.pdf_icon),
                                onCheckedChange = { updated ->
                                    viewModel.updateFocusItem(
                                        FocusReference(
                                            itemId = updated.id,
                                            sectionId = updated.sectionId,
                                            type = "pdf",
                                            title = updated.title,
                                            isCompleted = updated.isCompleted
                                        )
                                    )
                                },
                                onPdfClick = {
                                    if (!item.item.pdfUrl.isNullOrBlank()) {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            setDataAndType(Uri.parse(item.item.pdfUrl), "application/pdf")
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        context.startActivity(intent)
                                    }
                                },
                                menuItems = MenuOption.itemMenu.map { it.title },
                                onMenuItemClick = { title ->
                                    if (MenuOption.fromTitle(title) == MenuOption.Remove)
                                        viewModel.removeFocusItem(item.item.id)
                                }
                            )
                        }

                        is FocusUiItem.Note -> {
                            NoteComponent(
                                noteItem = item.item,
                                onCheckedChange = { updated ->
                                    viewModel.updateFocusItem(
                                        FocusReference(
                                            itemId = updated.id,
                                            sectionId = updated.sectionId,
                                            type = "note",
                                            title = updated.title,
                                            isCompleted = updated.isCompleted
                                        )
                                    )
                                },
                                onNoteClick = { /* Open note logic if needed */ },
                                menuItems = MenuOption.itemMenu.map { it.title },
                                onMenuItemClick = { title ->
                                    if (MenuOption.fromTitle(title) == MenuOption.Remove)
                                        viewModel.removeFocusItem(item.item.id)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            CustomProgressComponent(
                completed = uiState.focusItems.count { it.isCompleted },
                total = uiState.focusItems.size
            )


            Spacer(modifier = Modifier.height(14.dp))

            QuoteComponent(
                quote = quoteState.quoteText,
                author = quoteState.author,
                isLoading = quoteState.isLoading,
                errorMessage = quoteState.errorMessage
            )

            Spacer(modifier = Modifier.height(14.dp))

            LazyVerticalGrid(modifier = Modifier
                .weight(1f),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 70.dp)
            ) {
                items(uiState.sections) { section ->
                    SectionCardComponent(
                        sectionName = section.sectionName,
                        menuItems = MenuOption.sectionMenu.map { it.title },
                        onClick = {
                            navController.navigate("section/${section.section_id}")
                        },
                        onMenuItemClick = { selected ->
                            when (MenuOption.fromTitle(selected)) {
                                MenuOption.Edit -> {
                                    viewModel.onDialogEvent(
                                        DialogUiEvent.Show(
                                            DialogType.EDIT_SECTION,
                                            initialText = section.sectionName
                                        )
                                    )
                                }

                                MenuOption.Delete -> viewModel.deleteSection(section.section_id)
                                else -> Unit
                            }
                        }
                    )
                }
            }
        }

        InputDialogComponent(
            dialogTitle = when (dialogState.dialogType) {
                DialogType.ADD_SECTION -> "Add Section"
                DialogType.EDIT_SECTION -> "Edit Section"
                else -> ""
            },
            label = "Section Name",
            initialText = dialogState.inputValue,
            showDialog = dialogState.showDialog,
            isConfirmEnabled = dialogState.isValid,
            errorMessage = dialogState.errorMessage,
            onTextChanged = { viewModel.onDialogEvent(DialogUiEvent.InputChanged(it)) },
            onDismiss = { viewModel.onDialogEvent(DialogUiEvent.Dismiss) },
            onConfirm = {
                viewModel.onDialogEvent(DialogUiEvent.Submit)
            }
        )
    }
}


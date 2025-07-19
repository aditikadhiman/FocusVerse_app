// FILE: AddItemScreen.kt
package com.infinity.focusverse.uifiles.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.infinity.focusverse.R
import com.infinity.focusverse.state.AddItemEvent
import com.infinity.focusverse.state.addItem.DialogType
import com.infinity.focusverse.uifiles.Components.ActionCard
import com.infinity.focusverse.uifiles.Components.Heading3
import com.infinity.focusverse.uifiles.Components.InputDialogComponent
import com.infinity.focusverse.viewModel.AddItemViewModel

@Composable
fun AddItemScreen(
    sectionId: String,
    subSectionId: String,
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val dialogUiState = viewModel.uiState.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Heading3(value = "Add Item")
            ActionCard(icon = painterResource(id = R.drawable.ic_note), text = "Add Note") {
                viewModel.onEvent(AddItemEvent.ShowDialog(DialogType.ADD_NOTE))
            }
            ActionCard(icon = painterResource(id = R.drawable.ic_youtube), text = "Add YouTube Video") {
                viewModel.onEvent(AddItemEvent.ShowDialog(DialogType.ADD_VIDEO))
            }
            ActionCard(icon = painterResource(id = R.drawable.pdf_icon), text = "Add PDF") {
                viewModel.onEvent(AddItemEvent.ShowDialog(DialogType.ADD_PDF))
            }
            ActionCard(icon = painterResource(id = R.drawable.ic_subsection), text = "Add Subsection") {
                viewModel.onEvent(AddItemEvent.ShowDialog(DialogType.ADD_SUBSECTION))
            }
        }
    }

    if (dialogUiState.showDialog) {
        InputDialogComponent(
            dialogTitle = when (dialogUiState.dialogType) {
                DialogType.ADD_NOTE -> "Add Note"
                DialogType.ADD_VIDEO -> "Add YouTube Video (ID or Playlist ID)"
                DialogType.ADD_PDF -> "Add PDF (URL)"
                DialogType.ADD_SUBSECTION -> "Add Subsection"
                else -> ""
            },
            label = "Enter value",
            initialText = dialogUiState.inputValue,
            confirmButtonText = "Create",
            showDialog = true,
            isConfirmEnabled = dialogUiState.isValid,
            errorMessage = dialogUiState.errorMessage,
            onTextChanged = {
                viewModel.onEvent(AddItemEvent.InputChanged(it))
            },
            onDismiss = {
                viewModel.onEvent(AddItemEvent.Dismiss)
            },
            onConfirm = {
                viewModel.onEvent(AddItemEvent.Submit(sectionId, subSectionId))            }
        )
    }
}
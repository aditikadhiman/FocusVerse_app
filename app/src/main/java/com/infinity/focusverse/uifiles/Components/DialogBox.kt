package com.infinity.focusverse.uifiles.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.infinity.focusverse.ui.theme.*

@Composable
fun InputDialogComponent(
    dialogTitle: String,
    label: String = "Enter text",
    initialText: String = "",
    confirmButtonText: String = "Create",
    showDialog: Boolean,
    isConfirmEnabled: Boolean = true,
    errorMessage: String? = null,
    onTextChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    if (showDialog) {
        var text by remember { mutableStateOf(initialText) }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = background,
                elevation = 8.dp,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dialogTitle,
                        style = MaterialTheme.typography.h6.copy(color = TextColor),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            text = it
                            onTextChanged(it)
                        },
                        placeholder = { Text(label) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = TxtfieldColor.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = PrimaryButton,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        isError = errorMessage != null,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            style = MaterialTheme.typography.caption
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                onConfirm(text)
                                onDismiss()
                            },
                            enabled = isConfirmEnabled,
                            modifier = Modifier
                                .width(100.dp)
                                .height(49.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = PrimaryButton,
                                contentColor = TextColor,
                                disabledBackgroundColor = PrimaryButton,
                                disabledContentColor = MutedText
                            )
                        ) {
                            Text(
                                text = confirmButtonText,
                                style = MaterialTheme.typography.h2.copy(
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center,
                                    color = if (isConfirmEnabled) TextColor else MutedText
                                )
                            )
                        }

                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .width(100.dp)
                                .height(49.dp),
                            colors = ButtonDefaults.buttonColors(PrimaryButton)
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.h2.copy(
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


// For ADD Mode:
//InputDialogComponent(
//    dialogTitle = "Add Section",
//    showDialog = showAddDialog,
//    onTextChanged = { viewModel.onInputChanged(it) },
//    onDismiss = { viewModel.dismissDialog() },
//    onConfirm = { viewModel.saveSection(it) },
//    isConfirmEnabled = state.isValid,
//    errorMessage = state.errorMessage
//)
//✅ For EDIT Mode:
//InputDialogComponent(
//    dialogTitle = "Edit Note",
//    initialText = currentNoteTitle,
//    showDialog = showEditDialog,
//    onTextChanged = { viewModel.onInputChanged(it) },
//    onDismiss = { viewModel.dismissDialog() },
//    onConfirm = { viewModel.updateNoteTitle(it) },
//    isConfirmEnabled = state.isValid,
//    errorMessage = state.errorMessage
//)
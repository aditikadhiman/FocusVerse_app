package com.infinity.focusverse.uifiles.Components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.infinity.focusverse.ui.theme.SectionBox

//@Composable
//fun AddItemScreen(
//    onCreateSubsection: () -> Unit,
//    onAddYoutube: () -> Unit,
//    onAddPdf: () -> Unit,
//    onAddNote: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp),
//    ) {
//        ActionCard(icon = R.drawable.ic_subsection, text = "Create Subsection", onClick = onCreateSubsection)
//        ActionCard(icon = R.drawable.ic_youtube, text = "Add Youtube video", onClick = onAddYoutube)
//        ActionCard(icon = R.drawable.pdf_icon, text = "Add PDF", onClick = onAddPdf)
//        ActionCard(icon = R.drawable.ic_note, text = "Add note", onClick = onAddNote)
//    }
//}

@Composable
fun ActionCard(icon: Painter, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() },
        backgroundColor = SectionBox,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(painter = icon, contentDescription = null,tint=Color.Unspecified)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, color = Color.White)
        }
    }
}

//@Composable
//fun EditDialogComponent(
//    title: String,
//    initialText: String,
//    onDismissRequest: () -> Unit,
//    onConfirm: (String) -> Unit
//) {
//    var text by remember { mutableStateOf(initialText) }
//
//    AlertDialog(
//        onDismissRequest = onDismissRequest,
//        title = { Text(text = title) },
//        text = {
//            OutlinedTextField(
//                value = text,
//                onValueChange = { text = it },
//                label = { Text("Enter new text") },
//                modifier = Modifier.fillMaxWidth()
//            )
//        },
//        confirmButton = {
//            TextButton(onClick = {
//                onConfirm(text)
//                onDismissRequest()
//            }) {
//                Text("Save")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismissRequest) {
//                Text("Cancel")
//            }
//        }
//    )
//}


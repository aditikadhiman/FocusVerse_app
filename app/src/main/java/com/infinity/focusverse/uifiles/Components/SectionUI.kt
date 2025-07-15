package com.infinity.focusverse.uifiles.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.infinity.focusverse.model.NoteItem
import com.infinity.focusverse.model.PdfItem
import com.infinity.focusverse.model.VideoItem
import com.infinity.focusverse.ui.theme.SectionBox

@Composable
fun Heading3(value: String) {
    Text(
        text = value,
        style = MaterialTheme.typography.h1.copy(fontSize = 34.sp)
    )
}

@Composable
fun PdfComponent2(
    pdfItem: PdfItem,
    image: Painter,
    onCheckedChange: (PdfItem) -> Unit,
    onPdfClick: () -> Unit,
    menuItems: List<String>,
    onMenuItemClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .width(113.dp)
            .height(128.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = SectionBox,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onPdfClick() },
                        alignment = Alignment.Center, painter = image,
                        contentDescription = "pdf_icon"
                    )

//                    Spacer(modifier = Modifier.width(43.dp))

                    MenuComponent(menuItems = menuItems, onMenuItemClick = onMenuItemClick)
                }
//                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ExpandableText(text = pdfItem.title)
                    Spacer(modifier = Modifier.width(43.dp))
                    checkBox(
                        checked = pdfItem.isCompleted,
                        onCheckedChange = {
                            onCheckedChange(pdfItem.copy(isCompleted = it))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun VideoComponent2(
    videoItem: VideoItem,
    placeholder: Painter,
    errorImage: Painter,
    onCheckedChange: (VideoItem) -> Unit,
    menuItems: List<String>,
    onMenuItemClick: (String) -> Unit,
    onThumbnailClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(
                color = SectionBox,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(videoItem.thumbnailUrl)
                        .crossfade(true) // ✅ Fade-in effect
                        .build(),
                    contentDescription = "Youtube Thumbnail",
                    placeholder = placeholder,
                    error = errorImage,
                    modifier = Modifier
                        .height(110.dp)
                        .width(85.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { onThumbnailClick() }
                )

                Column(
                    modifier = Modifier
                        .width(190.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    ExpandableText(
                        text = videoItem.title,
                        style = MaterialTheme.typography.h4.copy(fontSize = 10.sp)
                    )
                    Text(
                        text = videoItem.channel,
                        style = MaterialTheme.typography.h4.copy(fontSize = 10.sp)
                    )
                    Text(
                        text = videoItem.duration,
                        style = MaterialTheme.typography.h4.copy(fontSize = 10.sp)
                    )
                }
                Column {
                    MenuComponent(
                        menuItems,
                        onMenuItemClick
                    )
                    checkBox(
                        checked = videoItem.isCompleted,
                        onCheckedChange = {
                            onCheckedChange(videoItem.copy(isCompleted = it))
                            //“Make a new VideoItem that has all the same values,
                            // except isCompleted, which should be set to it (true or false).”
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun NoteComponent2(
    noteItem: NoteItem,
    onCheckedChange: (NoteItem) -> Unit,
    onNoteClick: () -> Unit,
    menuItems: List<String>,
    onMenuItemClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = SectionBox,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                ExpandableText(text = noteItem.title,
                    modifier = Modifier
                        .width(317.dp)
                        .height(70.dp)
                        .clickable { onNoteClick() },
                    3)

                Column {
                    MenuComponent(
                        menuItems,
                        onMenuItemClick
                    )
                    checkBox(
                        checked = noteItem.isCompleted,
                        onCheckedChange = {
                            onCheckedChange(noteItem.copy(isCompleted = it))
                            //“Make a new VideoItem that has all the same values,
                            // except isCompleted, which should be set to it (true or false).”
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddButtonComponent(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = Color.Black.copy(alpha = 0.5f),
        contentColor = Color.White,
        shape = RoundedCornerShape(50),
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        modifier = Modifier
            .size(60.dp)
    ) {
        Text("+", fontSize = 42.sp)
    }
}


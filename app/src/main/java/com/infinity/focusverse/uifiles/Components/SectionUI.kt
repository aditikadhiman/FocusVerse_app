package com.infinity.focusverse.uifiles.Components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
            .width(132.dp)
            .height(128.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = SectionBox,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
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
                            .size(56.dp)
                            .clickable { onPdfClick() },
                        alignment = Alignment.Center, painter = image,
                        contentDescription = "pdf_icon"
                    )

//                    Spacer(modifier = Modifier.width(43.dp))

                    MenuComponent(menuItems = menuItems, onMenuItemClick = onMenuItemClick)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ExpandableText(text = pdfItem.title,          style = MaterialTheme.typography.h4.copy(fontSize = 12.sp))
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
                .background(color = SectionBox)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
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
                    contentScale=ContentScale.FillBounds,
                    modifier = Modifier
                        .height(90.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onThumbnailClick() }
                )
                
                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .width(190.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .heightIn(min = 30.dp,max=45.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        ExpandableText(
                            text = videoItem.title,
                            style = MaterialTheme.typography.h4.copy(fontSize = 12.sp)
                        )
                    }
                    Text(
                        text = videoItem.channel,
                        style = MaterialTheme.typography.h4.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = videoItem.duration,
                        style = MaterialTheme.typography.h4.copy(fontSize = 12.sp)
                    )
                }
                Column ( horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(20.dp)){
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
            .heightIn(128.dp), // ✅ Card is always fixed height
        shape = RoundedCornerShape(10.dp),
        backgroundColor = SectionBox
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SectionBox)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // ✅ Scrollable and expandable content within fixed card
                Box(
                    modifier = Modifier
                        .width(260.dp)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    ExpandableText(
                        text = noteItem.title,
                        collapsedMaxLines = 6,
                        style = MaterialTheme.typography.h4.copy(fontSize = 12.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNoteClick() }
                    )
                }

                // ✅ Menu + Checkbox on right
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    MenuComponent(
                        menuItems = menuItems,
                        onMenuItemClick = onMenuItemClick
                    )
                    checkBox(
                        checked = noteItem.isCompleted,
                        onCheckedChange = {
                            onCheckedChange(noteItem.copy(isCompleted = it))
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


package com.infinity.focusverse.uifiles.Components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.infinity.focusverse.model.NoteItem
import com.infinity.focusverse.model.PdfItem
import com.infinity.focusverse.model.VideoItem
import com.infinity.focusverse.ui.theme.*


val gradientBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFF1E1F5B), // Deep navy blue (left)
        Color(0xFF363AE4)  // Bright indigo/violet (right)
    ),
    start = Offset(500f, 0f),
    end = Offset(1000f, 500f) // Horizontal gradient (left to right)
)
//AppName
@Composable
fun Heading1(value1: String, value2: String, value3: String, value4: String) {
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Absolute.Right) {
        Text(
            text = value1,
            style = MaterialTheme.typography.h1.copy(fontSize = 26.sp, color = PrimaryButton)
        )
        Text(
            text = value2,
            style = MaterialTheme.typography.h1.copy(fontSize = 24.sp)
        )
        Text(
            text = value3,
            style = MaterialTheme.typography.h1.copy(fontSize = 26.sp, color = PrimaryButton)
        )
        Text(
            text = value4,
            style = MaterialTheme.typography.h1.copy(fontSize = 24.sp)
        )

    }
}


//Today'sFocusHeading
@Composable
fun Heading2(
    value: String, menuItems: List<String>,
    onMenuItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.h1.copy(fontSize = 30.sp)
        )
        MenuComponent(menuItems, onMenuItemClick)
    }
}


//Today'sFocusProgress1
@Composable
fun ProgressComponent(value: String, value1: Int, value2: Int) {
//    val progress = if (value2 != 0) value1.toFloat() / value2 else 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .width(350.dp)
                .background(
                    brush = gradientBrush,
                    shape = RoundedCornerShape(20.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.h6.copy(fontSize = 16.sp, color = TextColor)
                )
                Text(
                    text = "$value1/$value2",
                    style = MaterialTheme.typography.h6.copy(fontSize = 18.sp, color = TextColor)
                )
            }
        }
    }
}

@Composable
fun VideoComponent(
    videoItem: VideoItem,
    placeholder: Painter,
    errorImage: Painter,
    onCheckedChange: (VideoItem) -> Unit,
    menuItems: List<String>,
    onMenuItemClick: (String) -> Unit,
    onThumbnailClick: () -> Unit
) {
    val context = LocalContext.current
//    val thumbnailUrl = "https://img.youtube.com/vi/${videoItem.videoId}/hqdefault.jpg"
    Card(
        modifier = Modifier
            .width(155.dp)
            .height(184.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    if (videoItem.isCompleted) {
                        drawRect(Color.LightGray)
                    } else {
                        drawRect(brush = gradientBrush)
                    }
                }
                .padding(5.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(videoItem.thumbnailUrl)
                        .crossfade(true) // ✅ Fade-in effect
                        .build(),
                    contentDescription = "Youtube Thumbnail",
                    placeholder = placeholder,
                    contentScale = ContentScale.FillBounds, // ✅ Makes image fill and crop to modifier size
                    error = errorImage,
                    modifier = Modifier
                        .width(145.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { onThumbnailClick() }
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row() {
                    Column(
                        modifier = Modifier
                            .width(125.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(modifier = Modifier
                            .heightIn(min = 20.dp, max = 45.dp)
                            .verticalScroll(rememberScrollState())) {
                            ExpandableText(
                                text = videoItem.title,
                                style = MaterialTheme.typography.h4.copy(fontSize = 10.sp)
                            )
                        }
                        Text(
                            text = videoItem.channel,
                            style = MaterialTheme.typography.h4.copy(fontSize = 10.sp)
                        )
                        Text(
                            text = videoItem.duration,
                            style = MaterialTheme.typography.h4.copy(fontSize = 10.sp)
                        )
                    }
                    Column(horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween) {
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
}

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLines: Int = 2,
    style: TextStyle = MaterialTheme.typography.h4.copy(fontSize = 10.sp)
) {
    var expanded = remember { mutableStateOf(false) }

    Text(
        text = text,
        maxLines = if (expanded.value) Int.MAX_VALUE else collapsedMaxLines,
        overflow = TextOverflow.Ellipsis,
        style = style,
        modifier = modifier
            .clickable { expanded.value = !expanded.value }
            .animateContentSize()
    )
}


@Composable
fun PdfComponent(
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
            .height(108.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    if (pdfItem.isCompleted) {
                        drawRect(Color.LightGray)
                    } else {
                        drawRect(brush = gradientBrush)
                    }
                }
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
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
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
fun NoteComponent(
    noteItem: NoteItem,
    onCheckedChange: (NoteItem) -> Unit,
    onNoteClick: () -> Unit,
    menuItems: List<String>,
    onMenuItemClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(155.dp)
            .height(184.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    if (noteItem.isCompleted) {
                        drawRect(Color.LightGray)
                    } else {
                        drawRect(brush = gradientBrush)
                    }
                }
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
                ) {
                    Box( modifier = Modifier
                        .width(120.dp)
                        .height(140.dp)
                        .verticalScroll(rememberScrollState())) {
                        ExpandableText(
                            text = noteItem.title,
                            collapsedMaxLines = 11,
                            modifier = Modifier
                                .clickable { onNoteClick() }
                        )
                    }

                    MenuComponent(
                        menuItems = menuItems,
                        onMenuItemClick = onMenuItemClick
                    )
                }

                // ✅ Title as clickable to open the note


                // ✅ Custom Checkbox at the bottom
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


@Composable
fun CustomProgressComponent(
    completed: Int,
    total: Int
) {
    val progress = if (total > 0) completed.toFloat() / total else 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(0xFF1E1F5B),
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(50))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress)
                    .fillMaxHeight()
                    .background(
                        brush = gradientBrush,
                        shape = RoundedCornerShape(20.dp)
                    )
            )
        }
//        }
    }
}
//In your Home screen:
//CustomProgressComponent(completed = 3, total = 6)
//You can make these values dynamic using state from your ViewModel (e.g., HomeViewModel).

//val progress = completedItems.toFloat() / totalItems

@Composable
fun QuoteComponent(
    quote: String,
    author: String,
    isLoading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(color = background)
            .padding(horizontal = 0.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = background
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Loading motivational quote...",
                        style = MaterialTheme.typography.h4.copy(
                            color = TextColor.copy(alpha = 0.6f),
                            fontSize = 16.sp
                        )
                    )
                }
            }

            !errorMessage.isNullOrEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Believe you can and you're halfway there.",
                        style = MaterialTheme.typography.h1.copy(
                            color = TextColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Justify
                        )
                    )
                }
            }

            else -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "“$quote”",
                        style = MaterialTheme.typography.h1.copy(
                            color = TextColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Justify
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (author.isNotBlank()) {
                        Text(
                            text = "- $author",
                            style = MaterialTheme.typography.h1.copy(
                                color = TextColor,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                textAlign = TextAlign.End
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//fun QuoteComponent(quote: String) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth(),
//        backgroundColor = background
//    ) {
//        Text(
//            text = quote,
//            style = MaterialTheme.typography.h1.copy(color = TextColor, fontWeight = FontWeight.Normal, fontSize = 18.sp, textAlign = TextAlign.Justify)
//        )
//    }
//}


@Composable
fun SectionCardComponent(
    sectionName: String,
    onClick: () -> Unit,
    menuItems: List<String>,
    onMenuItemClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(113.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        backgroundColor = SectionBox
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Menu button at top-end
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                MenuComponent(
                    menuItems = menuItems,
                    onMenuItemClick = onMenuItemClick
                )
            }

            // Title centered in card
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = sectionName,
                    style = MaterialTheme.typography.h1.copy(
                        fontSize = 18.sp,
                        color = TextColor
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun BottomBarComponent(
    selectedTab: String = "Home",
    onClickHome: () -> Unit,
    onClickProfile: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        backgroundColor = SectionBox,
        elevation = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Half
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                BottomBarItem(
                    icon = Icons.Default.Home,
                    label = "Home",
                    isSelected = selectedTab == "Home",
                    onClick = onClickHome
                )
            }

            // Right Half
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                BottomBarItem(
                    icon = Icons.Default.Person,
                    label = "Profile",
                    isSelected = selectedTab == "Profile",
                    onClick = onClickProfile
                )
            }
        }
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val contentColor = if (isSelected) PrimaryButton else MutedText

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(64.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() }) // 👈 No ripple, no effect
            }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.caption.copy(
                fontSize = 12.sp,
                color = contentColor
            )
        )
    }
}


@Composable
fun checkBox(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {

    Checkbox(
        modifier = Modifier
            .width(20.dp)
            .height(20.dp),
        colors = CheckboxDefaults.colors(
            checkedColor = Color.Transparent,
            checkmarkColor = PrimaryButton,
            uncheckedColor = TextColor,
            disabledColor = Color.Transparent
        ), checked = checked,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun MenuComponent(menuItems: List<String>,
                  onMenuItemClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        //3-dot icon
        androidx.compose.material.IconButton(
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "menu"
            )
        }

        //Menu Dropdown
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            //we are looping through each menu option here
            menuItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onMenuItemClick(item)
                    }
                ) {
                    Text(text = item)
                }
            }

        }
    }
}

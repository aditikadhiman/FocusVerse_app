package com.infinity.focusverse.uifiles.Components

//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.Card
//import androidx.compose.material.Checkbox
//import androidx.compose.material.CheckboxDefaults
//import androidx.compose.material.DropdownMenuItem
//import androidx.compose.material.FloatingActionButton
//import androidx.compose.material.FloatingActionButtonDefaults
//import androidx.compose.material.Icon
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
//import androidx.compose.material.TextButton
//import androidx.compose.material.TextFieldDefaults
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.MoreVert
//import androidx.compose.material.DropdownMenu
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.infinity.focusverse.R
//import com.infinity.focusverse.model.NoteItem
//import com.infinity.focusverse.model.PdfItem
//import com.infinity.focusverse.model.VideoItem
//import com.infinity.focusverse.ui.theme.ComponentShapes
//import com.infinity.focusverse.ui.theme.MutedText
//import com.infinity.focusverse.ui.theme.PrimaryButton
//import com.infinity.focusverse.ui.theme.SectionBox
//import com.infinity.focusverse.ui.theme.TextColor
//import com.infinity.focusverse.ui.theme.TxtfieldColor
//import com.infinity.focusverse.ui.theme.background
//import java.net.URL

//@Composable
//fun ProfileScreen(
//    username: String,
//    streakCount: Int,
//    completionRate: Int,
//    onEditProfile: () -> Unit,
//    onLogout: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        // Profile Image Placeholder
//        Image(
//            painter = painterResource(id = R.drawable.profile_placeholder), // placeholder image
//            contentDescription = null,
//            modifier = Modifier
//                .size(100.dp)
//                .clip(RoundedCornerShape(50))
//        )
//
//        Text(text = username, style = MaterialTheme.typography.h4.copy(color = TextColor))
//
//        InfoCard(icon = R.drawable.ic_fire, title = "Streak", value = "$streakCount days")
////        InfoCard(icon = R.drawable.ic_progress, title = "Completion Rate", value = "$completionRate%")
//
//        ActionCard(icon = R.drawable.ic_edit, text = "Edit Profile", onClick = onEditProfile)
//        ActionCard(icon = R.drawable.ic_logout, text = "Log out", onClick = onLogout)
//    }
//}
//
//@Composable
//fun InfoCard(icon: Int, title: String, value: String) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        backgroundColor = SectionBox,
//        shape = RoundedCornerShape(12.dp),
//        elevation = 6.dp
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(painter = painterResource(id = icon), contentDescription = null, tint = Color.White)
//                Spacer(modifier = Modifier.width(12.dp))
//                Text(text = title, color = Color.White)
//            }
//            Text(text = value, color = Color.White)
//        }
//    }
//}

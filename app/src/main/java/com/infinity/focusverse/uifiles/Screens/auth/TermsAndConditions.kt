package com.infinity.focusverse.uifiles.Screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TermsAndConditionsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms & Conditions") },
                backgroundColor = Color(0xFF1E1F5B),
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Welcome to FocusVerse!",
                    style = MaterialTheme.typography.h6,
                    color = Color(0xFF1E1F5B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = """
                        By using this app, you agree to the following terms and conditions:
                        
                        1. You are responsible for your own data.
                        2. We do not share your personal information.
                        3. The app uses Firebase and YouTube APIs.
                        4. Content completion data is stored locally or on Firebase.
                        5. Do not misuse or tamper with app functionality.
                        6. External links (e.g. PDFs, YouTube) are owned by their respective platforms.
                        7. We may update these terms in the future.

                        Thank you for using FocusVerse to boost your productivity!
                    """.trimIndent(),
                    style = MaterialTheme.typography.body1,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Last updated: July 2025",
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
            }
        }
    )
}

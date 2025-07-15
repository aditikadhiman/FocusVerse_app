package com.infinity.focusverse.uifiles.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.infinity.focusverse.R
import com.infinity.focusverse.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(60.dp),
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center
    )
}

@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier.width(303.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = PrimaryButton,
            thickness = 1.dp
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.or),
            style = MaterialTheme.typography.h3

        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = PrimaryButton,
            thickness = 1.dp
        )

    }

}

@Composable
fun ButtonTransparentComponent(value: String,onButtonClicked: () -> Unit) {
    Button(
        onClick = {
                  onButtonClicked.invoke()
        },
        modifier = Modifier
            .width(303.dp)
            .heightIn(59.dp),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        border = BorderStroke(1.dp, PrimaryButton)
    ) {
        Box(
            modifier = Modifier
                .width(303.dp)
                .heightIn(59.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(12.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.h2
            )
        }

    }
}

@Composable
fun ButtonComponent(value: String,onButtonClicked: ()->Unit) {
    Button(
        onClick = {
                  onButtonClicked.invoke()
        },
        modifier = Modifier
            .width(303.dp)
            .heightIn(59.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(PrimaryButton),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .width(303.dp)
                .heightIn(59.dp)
                .background(
                    color = PrimaryButton,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Composable
fun ContinueWithGoogleButton(value: String, painter: Painter,onButtonClicked: () ->  Unit) {
    Button(
        onClick = {
                  onButtonClicked.invoke()
        },
        modifier = Modifier
            .width(303.dp)
            .heightIn(59.dp),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        border = BorderStroke(1.dp, PrimaryButton)
    ) {
        Box(
            modifier = Modifier
                .width(303.dp)
                .heightIn(59.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(12.dp)
                ), contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Google logo",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }
}

@Composable
fun Button2Component(value: String,onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(
        onClick = {
                  onButtonClicked.invoke()
        },
        modifier = Modifier
            .width(330.dp)
            .heightIn(59.dp),
        colors = ButtonDefaults.buttonColors(PrimaryButton),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(59.dp)
                .background(
                    color = PrimaryButton,
                    shape = RoundedCornerShape(12.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.h2.copy(
                    color = if (isEnabled) TextColor else MutedText
                )
            )
        }
    }
}

@Composable
fun UnderlinedTextComponent(value: String,onClick: () -> Unit) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .clickable { onClick() }, //make it clickable
        style = MaterialTheme.typography.h3.copy(fontSize=18.sp),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}


@Composable
fun PasswordTextFieldComposable(
    labelValue: String,
    imageVector: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val password = rememberSaveable {
        mutableStateOf("")
    }

    val passwordVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val localFocusManager = LocalFocusManager.current

//    val borderColor = if (!errorStatus) PrimaryButton else Color.Red

    OutlinedTextField(
        shape=RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(330.dp)
            .height(59.dp)
            .background(
                color = TxtfieldColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = PrimaryButton,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(ComponentShapes.small),
        placeholder= { Text(text = labelValue, style = MaterialTheme.typography.h4.copy(fontSize=16.sp)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrimaryButton,
            focusedLabelColor = Color.Black,
            cursorColor = PrimaryButton,
            backgroundColor = Color.Transparent
        ),
        value = password.value,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
            //keyboard to hide when done is clicked  for outside the
            // field click you can put the ui in a box or column
            // and use a clickable modifier to detect clicks outside the TextField.
            // When a click is detected, the focus is cleared, which typically causes the keyboard to hide.
        },
        singleLine = true,
        maxLines = 1,
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = "profile") },
        trailingIcon = {

            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (passwordVisible.value) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else
            PasswordVisualTransformation(),
        isError=!errorStatus
    )


}


@Composable
fun MyTextFieldComponent(
    labelValue: String,
    imageVector: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val textValue = rememberSaveable {
        mutableStateOf("")
    }

//    val borderColor = if (!errorStatus) PrimaryButton else Color.Red

    OutlinedTextField(modifier = Modifier
        .width(330.dp)
        .height(59.dp)
        .background(
            color = TxtfieldColor.copy(alpha = 0.2f),
            shape = RoundedCornerShape(12.dp)
        )
        .border(
            width = 1.dp,
            color = PrimaryButton,
            shape = RoundedCornerShape(12.dp)
        )
        .clip(ComponentShapes.small),
        placeholder={Text(text = labelValue, style = MaterialTheme.typography.h4.copy(fontSize=16.sp))},
//        label = { Text(text = labelValue, style = MaterialTheme.typography.h4.copy(fontSize=16.sp)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrimaryButton,
            focusedLabelColor = Color.Black,
            cursorColor = PrimaryButton,
            backgroundColor = Color.Transparent
        ),
        value = textValue.value,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        }, leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = "profile")
        },
        shape=RoundedCornerShape(12.dp),
        isError =!errorStatus //automatically changes colour as per error
    )
}


@Composable
fun CheckBoxComponent(
    value: String,
    onTextSelected: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .width(330.dp)
            .heightIn(59.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement=Arrangement.spacedBy(15.dp)
    ) {
        val checkedState = remember {
            mutableStateOf(false)
        }
        Spacer(modifier= Modifier.width(2.dp))
        Checkbox(modifier = Modifier
            .width(20.dp)
            .height(20.dp), colors = CheckboxDefaults.colors(
            checkedColor = Color.Transparent,
            checkmarkColor = PrimaryButton,
            uncheckedColor = TextColor,
            disabledColor = Color.Transparent
        ), checked = checkedState.value, onCheckedChange = {
            checkedState.value = !checkedState.value
            onCheckedChange.invoke(it)
        })

        ClickableTextComponent(value, onTextSelected)
    }
}

@Composable
fun ClickableTextComponent(value: String, onTextSelected: (String) -> Unit) {
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy "
    val andText = "and "
    val termsAndConditionText = "Terms of Use"

    var annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = TextColor
            )
        ) {
            pushStringAnnotation(tag = "", annotation = "")
            append(initialText)
        }
        withStyle(
            style = SpanStyle(
                color = PrimaryButton
            )
        ) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(
            style = SpanStyle(
                color = PrimaryButton
            )
        ) {
            pushStringAnnotation(tag = termsAndConditionText, annotation = termsAndConditionText)
            append(termsAndConditionText)
        }
    }
    ClickableText(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 40.dp),//it is minimum not fixed
        style = MaterialTheme.typography.h3.copy(textAlign=TextAlign.Start,fontSize=15.sp),text = annotatedString, onClick = {
        onTextSelected(value)
    })
}

@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {

    val initialText =
        if (tryingToLogin) "Already have an account? " else "Don't have an account yet? "
    val LoginText = if (tryingToLogin) "Login" else "Register"


    var annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = TextColor
            )
        ) {
            pushStringAnnotation(tag = "", annotation = "")

            append(initialText)
        }
        withStyle(
            style = SpanStyle(
                color = PrimaryButton
            )
        ) {
            pushStringAnnotation(tag = LoginText, annotation = LoginText)
            append(LoginText)
        }

    }

    ClickableText(
        modifier = Modifier
            .width(300.dp)
            .heightIn(min = 40.dp),//it is minimum not fixed
        style = MaterialTheme.typography.h3.copy(textAlign=TextAlign.Center,fontSize=18.sp),
        text = annotatedString, onClick = {
            onTextSelected(LoginText)

        })

}

@Composable
fun ResetPasswordDialog(  // add is enable for muted color
    showDialog: Boolean,
    onTextSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    onSendLink: (String) -> Unit,
    isEnabled: Boolean= false,
    errorStatus: Boolean = false
) {
    if (showDialog) {
        var email = remember {
            mutableStateOf("")
        }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = background,
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.reset_password),
                        style = MaterialTheme.typography.h6.copy(color = Color.White),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = email.value,
                        onValueChange = {
                            email.value = it
                            onTextSelected(it)
                        },
                        label = { Text("Enter your email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = PrimaryButton,
                            focusedLabelColor = TextColor,
                            cursorColor = PrimaryButton,
                            backgroundColor = Color.Transparent
                        ),isError = !errorStatus
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically){
                        Button(
                            onClick = {
                                onSendLink(email.value)
                                onDismiss()
                            },
                            modifier = Modifier
                                .width(100.dp)
                                .height(49.dp),
                            enabled = isEnabled,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = PrimaryButton,  // Purple even if disabled
                                contentColor = TextColor,
                                disabledBackgroundColor = PrimaryButton,  // Override grey!
                                disabledContentColor = MutedText          // Slightly muted text
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.send_link),
                                style = MaterialTheme.typography.h2.copy(fontSize = 15.sp,textAlign = TextAlign.Center,color=if (isEnabled) TextColor else MutedText)
                            )
                        }
                        Spacer(modifier= Modifier.width(20.dp))
                            TextButton(modifier = Modifier
                                .width(100.dp)
                                .height(49.dp)
                                ,onClick = onDismiss,colors = ButtonDefaults.buttonColors(PrimaryButton)) {
                                Text(text = stringResource(id = R.string.cancel), style = MaterialTheme.typography.h2.copy(fontSize = 15.sp,textAlign = TextAlign.Center))
                            }
                    }
                }
            }
        }
    }
}


//🧍User types or taps → 🎯 UIEvent is sent → 🧠 ViewModel updates UIState → 📱 UI shows changes
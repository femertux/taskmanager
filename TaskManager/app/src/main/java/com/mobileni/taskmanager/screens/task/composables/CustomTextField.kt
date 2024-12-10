package com.mobileni.taskmanager.screens.task.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileni.taskmanager.ui.theme.TaskPalette
import com.mobileni.taskmanager.ui.theme.fonts

@Composable
fun CustomTextField(
    title: String,
    placeholder: String,
    initialValue: String,
    maxLength: Int = 40,
    onChange: (text: String) -> Unit = { }
) {

    var textState by remember { mutableStateOf(TextFieldValue(initialValue)) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .wrapContentHeight(),
    ) {
        Text(
            text = title,
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TaskPalette.darkBlue,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .height(48.dp)
                .border(
                    width = 1.dp,
                    color = TaskPalette.darkGray,
                    RoundedCornerShape(8.dp),
                )
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(
                    end = 16.dp,
                    start = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            horizontalArrangement = Arrangement.Start
        ) {
            BasicTextField(
                value = textState,
                onValueChange = {
                    if (it.text.length <= maxLength) {
                        textState = it
                        onChange(it.text)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus(true)
                    }
                ),
                textStyle = TextStyle(
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = TaskPalette.blackText,
                    textAlign = TextAlign.Start,
                    letterSpacing = 0.sp
                ),
                visualTransformation = VisualTransformation.None,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (textState.text.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = TaskPalette.grayText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    Column(
        modifier = Modifier
            .background(TaskPalette.gray)
    ) {
        CustomTextField(
            initialValue = "",
            title = "Nombre de la tarea",
            placeholder = "Nombre",
            onChange = {}
        )
    }
}
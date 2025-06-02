package com.specialtech.diary.ui.features.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DiaryTextField(
    value: String,
    placeholder: @Composable (() -> Unit)? = null,
    singleLine: Boolean,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    TextField(
        value = value,
        placeholder = placeholder,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            unfocusedTextColor = Color.White,
            unfocusedPlaceholderColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedTextColor = Color.White,
            focusedPlaceholderColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                handleColor = Color.White,
                backgroundColor = Color.Transparent
            ),
            cursorColor = Color.White
        ),
        onValueChange = onValueChange,
        modifier = modifier
    )
}
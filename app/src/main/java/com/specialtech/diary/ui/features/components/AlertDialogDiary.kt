package com.specialtech.diary.ui.features.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AlertDialogDiary(
    onDismissRequest:() -> Unit,
    onConfirm:() -> Unit,
    onCancel:() -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview
@Composable
private fun AlertDialogDiaryPreview() {
    AlertDialogDiary(
        onDismissRequest = {},
        onConfirm = {},
        onCancel = {},
        dialogTitle = "Delete Note",
        dialogText = "Delete Note #5?",
        icon = Icons.Filled.Delete
    )
}


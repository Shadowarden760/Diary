package com.specialtech.diary.ui.features.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.specialtech.diary.R

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
                Text(text = stringResource(R.string.dialog_text_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text(text = stringResource(R.string.dialog_text_cancel))
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


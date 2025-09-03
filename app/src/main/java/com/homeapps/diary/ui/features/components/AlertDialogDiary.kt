package com.homeapps.diary.ui.features.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R

@Composable
fun AlertDialogDiary(
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    onConfirm:() -> Unit,
    onCancel:() -> Unit,
    onDismissRequest:() -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        },
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(R.string.dialog_text_confirm),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text(
                    text = stringResource(R.string.dialog_text_cancel),
                    style = MaterialTheme.typography.titleMedium
                )
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
        dialogText = "Delete Note with title 'Shopping'?",
        icon = Icons.Filled.Info
    )
}
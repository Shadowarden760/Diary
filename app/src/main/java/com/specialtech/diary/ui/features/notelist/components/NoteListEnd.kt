package com.specialtech.diary.ui.features.notelist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun NoteListEnd(
    goToNoteDetail:(noteId: Long) -> Unit = {},
    createNewNote:() -> Long,
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = {
                val newId = createNewNote()
                goToNoteDetail(newId)
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun NoteListEndPreview() {
    NoteListEnd({}, { 1L })
}

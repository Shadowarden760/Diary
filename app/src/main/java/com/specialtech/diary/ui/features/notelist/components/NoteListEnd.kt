package com.specialtech.diary.ui.features.notelist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.specialtech.diary.ui.theme.MainDark
import com.specialtech.diary.ui.theme.MainOrange


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
            shape = RoundedCornerShape(6.dp),
            containerColor = MainOrange,
            contentColor = MainDark,
            onClick = {
                val newId = createNewNote()
                goToNoteDetail(newId)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
    }
}

@Preview
@Composable
fun NoteListEndPreview() {
    NoteListEnd({}, { 1L })
}

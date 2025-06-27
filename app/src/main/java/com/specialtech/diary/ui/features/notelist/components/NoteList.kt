package com.specialtech.diary.ui.features.notelist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.specialtech.diary.Note
import com.specialtech.diary.R
import com.specialtech.diary.ui.features.components.AlertDialogDiary


@Composable
fun NoteList(
    noteList: List<Note>,
    goToNoteDetail:(noteId: Long) -> Unit,
    deleteNote:(noteId: Long) -> Unit
) {
    val openAlertDialogNoteId = remember { mutableLongStateOf(0L) }

    if (openAlertDialogNoteId.longValue > 0L) {
        AlertDialogDiary(
            onDismissRequest = { openAlertDialogNoteId.longValue = 0L },
            onConfirm = {
                deleteNote(openAlertDialogNoteId.longValue)
                openAlertDialogNoteId.longValue = 0L
            },
            onCancel = { openAlertDialogNoteId.longValue = 0L },
            dialogTitle = stringResource(
                R.string.note_list_text_delete_note_title,
                openAlertDialogNoteId.longValue
            ),
            dialogText = stringResource(
                R.string.note_list_text_delete_note_content,
                noteList.find { it.noteId == openAlertDialogNoteId.longValue }?.noteTitle.toString()
            ),
            icon = Icons.Filled.Info
        )
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        items(noteList) { note ->
            NoteListItem(
                note = note,
                onItemClick = {
                    println("${note.noteId} clicked")
                    goToNoteDetail(note.noteId)
                },
                onLongItemClick = {
                    println("${note.noteId} long-clicked")
                    openAlertDialogNoteId.longValue = note.noteId
                }
            )
        }
    }
}

@Preview
@Composable
private fun NoteListPreview() {
    NoteList(listOf(
        Note(1, "category", "title1",0,  1287371236786),
        Note(2, "category", "title2", 0,1287371236786),
        Note(3, "category", "title3", 0, 1287371236786),
        Note(4, "category", "title4", 0, 1287371236786)
    ), {}, {})
}
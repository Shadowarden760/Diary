package com.homeapps.diary.ui.features.notelist.components

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
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.ui.features.components.AlertDialogDiary

@Composable
fun NoteList(
    noteList: List<NoteData>,
    goToNoteDetail:(noteId: Long) -> Unit,
    deleteNote:(noteId: Long) -> Unit
) {
    val openAlertDialogNoteId = remember { mutableLongStateOf(0L) }

    if (openAlertDialogNoteId.longValue > 0L) {
        AlertDialogDiary(
            dialogTitle = stringResource(R.string.note_list_text_delete_note_title),
            dialogText = stringResource(
                R.string.note_list_text_delete_note_content,
                noteList.find { it.noteId == openAlertDialogNoteId.longValue }?.noteTitle.toString()
            ),
            icon = Icons.Filled.Info,
            onConfirm = {
                deleteNote(openAlertDialogNoteId.longValue)
                openAlertDialogNoteId.longValue = 0L
            },
            onCancel = { openAlertDialogNoteId.longValue = 0L },
            onDismissRequest = { openAlertDialogNoteId.longValue = 0L },
        )
    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
    ) {
        items(noteList) { note ->
            NoteListItem(
                note = note,
                onItemClick = {
                    goToNoteDetail(note.noteId)
                },
                onLongItemClick = {
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
        NoteData(1, "category", "title1",0,  1287371236786),
        NoteData(2, "category", "title2", 0,1287371236786),
        NoteData(3, "category", "title3", 0, 1287371236786),
        NoteData(4, "category", "title4", 0, 1287371236786)
    ), {}, {})
}
package com.homeapps.diary.ui.features.notelist.components

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.ui.features.components.AlertDialogDiary
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyStaggeredGridState

@Composable
fun NoteList(
    noteList: List<NoteData>,
    goToNoteDetail:(noteId: Long) -> Unit,
    updateNoteOrder:(orderedNotes: List<NoteData>) -> Unit,
    deleteNote:(noteId: Long) -> Unit
) {
    val notesOrder = remember { mutableStateOf(listOf<NoteData>()) }
    val openAlertDialogNoteId = remember { mutableLongStateOf(0L) }
    val hapticFeedback = LocalHapticFeedback.current
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val reorderableLazyStaggeredGridState = rememberReorderableLazyStaggeredGridState(lazyStaggeredGridState) { from, to ->
        notesOrder.value = notesOrder.value.toMutableList().apply {
            this[to.index] = this[from.index].copy(noteOrder = to.index + 1L)
                .also {
                    this[from.index] = this[to.index].copy(noteOrder = from.index + 1L)
                }
        }
        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    LaunchedEffect(noteList) {
        notesOrder.value = noteList.sortedBy { it.noteOrder }
    }

    if (openAlertDialogNoteId.longValue > 0L) {
        AlertDialogDiary(
            dialogTitle = stringResource(R.string.note_list_text_delete_note_title),
            dialogText = stringResource(
                R.string.note_list_text_delete_note_content,
                notesOrder.value.find { it.noteId == openAlertDialogNoteId.longValue }?.noteTitle.toString()
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
        state = lazyStaggeredGridState,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
    ) {
        items(notesOrder.value, key = { it.noteId }) { note ->
            ReorderableItem(
                state = reorderableLazyStaggeredGridState,
                key = note.noteId
            ) { _ ->
                val interactionSource = remember { MutableInteractionSource() }
                val modifier = Modifier.draggableHandle(
                    onDragStarted = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
                    },
                    onDragStopped = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureEnd)
                    },
                    interactionSource = interactionSource
                )

                NoteListItem(
                    note = note,
                    onItemClick = { goToNoteDetail(note.noteId) },
                    onLongItemClick = { openAlertDialogNoteId.longValue = note.noteId },
                    interactionSource = interactionSource,
                    modifier = modifier
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            updateNoteOrder(notesOrder.value)
        }
    }
}

@Preview
@Composable
private fun NoteListPreview() {
    NoteList(listOf(
        NoteData(1, "category", "title1", 0,0,  1287371236786),
        NoteData(2, "category", "title2", 0, 0,1287371236786),
        NoteData(3, "category", "title3", 0, 0, 1287371236786),
        NoteData(4, "category", "title4", 0, 0, 1287371236786)
    ), {}, {}, {})
}
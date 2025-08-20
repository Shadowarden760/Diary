package com.specialtech.diary.ui.features.notedetail

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.specialtech.diary.Note
import com.specialtech.diary.ui.features.notedetail.components.NoteData
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel


@Composable
fun NoteDetailScreen(
    viewModel: NoteDetailViewModel = koinViewModel(),
    noteId: Long,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    goToNoteList: () -> Unit
) {
    val currentNote = viewModel.getCurrentNote(noteId = noteId)
    val hasStoragePermission = remember { mutableStateOf(false) }
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasStoragePermission.value = permissions.all { it.value }
        if (hasStoragePermission.value) {
            val result = viewModel.saveNoteToFile(noteId = noteId)
            viewModel.showSnackBar(
                eventResult = result != null,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                action = { viewModel.deleteNoteFile(result) }
            )
        }
    }

    NoteData(
        noteData = currentNote,
        updateNote = { newTitle: String, newMessage: String ->
            val updatedNote = Note(
                noteId = currentNote.noteId,
                noteTitle = newTitle,
                noteMessage = newMessage,
                noteUpdatedAt = currentNote.noteUpdatedAt,
                noteCreatedAt = currentNote.noteCreatedAt
            )
            viewModel.saveUpdatedNote(updatedNote)
        }
    )
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = {
                if (viewModel.checkStoragePermissions(storagePermissionLauncher)) {
                    val result = viewModel.saveNoteToFile(noteId = noteId)
                    viewModel.showSnackBar(
                        eventResult = result != null,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState,
                        action = { viewModel.deleteNoteFile(result) }
                    )
                }
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null
            )
        }
    }
}
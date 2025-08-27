package com.specialtech.diary.ui.features.notedetail

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.specialtech.diary.Note
import com.specialtech.diary.R
import com.specialtech.diary.ui.features.notedetail.components.NoteButtons
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
    val currentContext = LocalContext.current
    val currentNote = viewModel.getCurrentNote(noteId = noteId)
    val hasStoragePermission = remember { mutableStateOf(false) }
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasStoragePermission.value = permissions.all { it.value }
        if (hasStoragePermission.value) {
            viewModel.saveNoteToFile(
                noteId = noteId,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState
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
    NoteButtons(
        shareNote = {
            viewModel.shareNoteText(
                noteId = noteId,
                chooserTitle = currentContext.getString(R.string.note_detail_text_share_with),
                context = currentContext
            )
        },
        saveNote = {
            if (viewModel.checkStoragePermissions(storagePermissionLauncher)) {
                viewModel.saveNoteToFile(
                    noteId = noteId,
                    coroutineScope = coroutineScope,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    )
}
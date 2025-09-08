package com.homeapps.diary.ui.features.notedetail

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.ui.features.notedetail.components.NoteButtons
import com.homeapps.diary.ui.features.notedetail.components.NoteDataScreen
import com.homeapps.diary.utils.DiarySnackBarManager
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteDetailScreen(
    viewModel: NoteDetailViewModel = koinViewModel(),
    noteId: Long,
    snackBarManager: DiarySnackBarManager,
    goToNoteList: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentContext = LocalContext.current
    val hasStoragePermission = remember { mutableStateOf(false) }
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasStoragePermission.value = permissions.all { it.value }
        if (hasStoragePermission.value) {
            viewModel.saveNoteToFile(noteId = noteId, snackBarManager = snackBarManager)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCurrentNote(noteId = noteId)
    }

    when(state.value) {
        is NoteDetailViewModel.NoteDetailState.CurrentNote -> {
            val note = (state.value as NoteDetailViewModel.NoteDetailState.CurrentNote).note
            NoteDataScreen(
                noteData = note,
                updateNote = { newTitle: String, newMessage: String ->
                    val updatedNote = NoteData(
                        noteId = note.noteId,
                        noteTitle = newTitle,
                        noteMessage = newMessage,
                        noteUpdatedAt = note.noteUpdatedAt,
                        noteCreatedAt = note.noteCreatedAt
                    )
                    viewModel.saveUpdatedNote(updatedNote)
                }
            )
            NoteButtons(
                shareNote = {
                    viewModel.shareNoteText(
                        noteId = noteId,
                        chooserTitle = currentContext.getString(R.string.note_detail_text_share_with)
                    )
                },
                saveNote = {
                    if (viewModel.hasStoragePermissions()) {
                        viewModel.saveNoteToFile(noteId = noteId, snackBarManager = snackBarManager)
                    } else {
                        viewModel.requestStoragePermissions(storagePermissionLauncher)
                    }
                }
            )
        }
        is NoteDetailViewModel.NoteDetailState.Error -> { goToNoteList() }
        is NoteDetailViewModel.NoteDetailState.Default -> { /* do nothing */ }
    }
}
package com.homeapps.diary.ui.features.notedetail

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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
    val currentContext = LocalContext.current
    val currentNote = viewModel.getCurrentNote(noteId = noteId)
    val hasStoragePermission = remember { mutableStateOf(false) }
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasStoragePermission.value = permissions.all { it.value }
        if (hasStoragePermission.value) {
            viewModel.saveNoteToFile(noteId = noteId, snackBarManager = snackBarManager)
        }
    }

    if (currentNote != null) {
        NoteDataScreen(
            noteData = currentNote,
            updateNote = { newTitle: String, newMessage: String ->
                val updatedNote = NoteData(
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
                if (viewModel.hasStoragePermissions()) {
                    viewModel.saveNoteToFile(noteId = noteId, snackBarManager = snackBarManager)
                } else {
                    viewModel.requestStoragePermissions(storagePermissionLauncher)
                }
            }
        )
    } else {
        goToNoteList()
    }
}
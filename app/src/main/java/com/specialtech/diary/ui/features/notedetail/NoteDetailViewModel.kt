package com.specialtech.diary.ui.features.notedetail

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.specialtech.diary.Note
import com.specialtech.diary.R
import com.specialtech.diary.data.repositories.NotesRepository
import com.specialtech.diary.utils.DiaryFileManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val notesRepository: NotesRepository,
    private val appContext: Context,
): ViewModel() {
    private val diaryFileManager = DiaryFileManager()

    fun getCurrentNote(noteId: Long) = notesRepository.getUserNoteById(noteId = noteId)

    fun saveUpdatedNote(updatedNote: Note) = notesRepository.updateNote(updatedNote)

    fun saveNoteToFile(noteId: Long): String? {
        val note = getCurrentNote(noteId = noteId)
        return diaryFileManager.saveDataToFile(
            data = note,
            fileName = "${note.noteTitle.filterNot { it.isWhitespace() }}_${note.noteId}.json"
        )
    }

    fun deleteNoteFile(filePath: String?): Boolean {
        return diaryFileManager.deleteFile(filePath = filePath)
    }

    fun checkStoragePermissions(launcher: ActivityResultLauncher<Array<String>>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return true
        }
        val storagePermissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return if (storagePermissions.all { ContextCompat.checkSelfPermission(appContext, it) == PackageManager.PERMISSION_GRANTED }) {
            true
        } else {
            launcher.launch(storagePermissions)
            false
        }
    }

    fun showSnackBar(
        eventResult: Boolean,
        coroutineScope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        action: () -> Unit = {},
    ) = coroutineScope.launch {
        if (eventResult) {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = appContext.getString(R.string.note_detail_text_file_was_saved),
                actionLabel = appContext.getString(R.string.note_detail_text_file_was_saved_cancellation),
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            when (snackbarResult) {
                SnackbarResult.ActionPerformed -> { action() }
                SnackbarResult.Dismissed -> { /* do nothing */ }
            }
        } else {
            snackbarHostState.showSnackbar(
                message = appContext.getString(R.string.note_detail_text_file_was_not_saved),
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }
}
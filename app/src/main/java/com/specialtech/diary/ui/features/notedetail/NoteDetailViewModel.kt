package com.specialtech.diary.ui.features.notedetail

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.specialtech.diary.Note
import com.specialtech.diary.R
import com.specialtech.diary.data.repositories.NotesRepository
import com.specialtech.diary.utils.DiaryFileManager
import com.specialtech.diary.utils.DiaryShareManager
import com.specialtech.diary.utils.DiarySnackBarManager

class NoteDetailViewModel(
    private val notesRepository: NotesRepository,
    private val appContext: Context,
): ViewModel() {
    private val diaryFileManager = DiaryFileManager()
    private val diaryShareManager = DiaryShareManager()

    fun getCurrentNote(noteId: Long) = notesRepository.getUserNoteById(noteId = noteId)

    fun saveUpdatedNote(updatedNote: Note) = notesRepository.updateNote(updatedNote)

    fun shareNoteText(noteId: Long, chooserTitle: String, context: Context) {
        val note = getCurrentNote(noteId = noteId)
        val result = diaryShareManager.shareTextData(
            textData = note.noteMessage,
            chooserTitle = chooserTitle,
            context = context
        )
        println(result)
    }

    fun saveNoteToFile(noteId: Long, snackBarManager: DiarySnackBarManager) {
        val note = getCurrentNote(noteId = noteId)
        val result = diaryFileManager.saveDataToFile(
            data = note,
            fileName = "${note.noteTitle.filterNot { it.isWhitespace() }}_${note.noteId}.json"
        )
        var message: String
        var actionLabel: String? = null
        var action: () -> Unit = {}
        if (result != null) {
            message = appContext.getString(R.string.note_detail_text_file_was_saved)
            actionLabel = appContext.getString(R.string.note_detail_text_file_was_saved_cancellation)
            action = { deleteNoteFile(result) }
        } else {
            message = appContext.getString(R.string.note_detail_text_file_was_not_saved)
        }
        snackBarManager.showSnackBar(
            message = message,
            actionLabel = actionLabel,
            action = action
        )
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

    private fun deleteNoteFile(filePath: String?): Boolean {
        return diaryFileManager.deleteFile(filePath = filePath)
    }
}
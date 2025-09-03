package com.homeapps.diary.ui.features.notedetail

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.homeapps.diary.Note
import com.homeapps.diary.R
import com.homeapps.diary.data.repositories.NotesRepository
import com.homeapps.diary.utils.DiaryFileManager
import com.homeapps.diary.utils.DiaryShareManager
import com.homeapps.diary.utils.DiarySnackBarManager

class NoteDetailViewModel(
    private val notesRepository: NotesRepository,
    private val appContext: Context,
): ViewModel() {
    private val diaryFileManager = DiaryFileManager(appContext)
    private val diaryShareManager = DiaryShareManager()

    fun getCurrentNote(noteId: Long) = notesRepository.getUserNoteById(noteId = noteId)

    fun saveUpdatedNote(updatedNote: Note) = notesRepository.updateNote(updatedNote)

    fun shareNoteText(noteId: Long, chooserTitle: String, context: Context) {
        val note = getCurrentNote(noteId = noteId) ?: return
        diaryShareManager.shareTextData(
            textData = note.noteMessage,
            chooserTitle = chooserTitle,
            context = context
        )
    }

    fun hasStoragePermissions(): Boolean = diaryFileManager.hasStoragePermissions()

    fun requestStoragePermissions(launcher: ActivityResultLauncher<Array<String>>) {
        launcher.launch(diaryFileManager.storagePermissions)
    }

    fun saveNoteToFile(noteId: Long, snackBarManager: DiarySnackBarManager) {
        val note = getCurrentNote(noteId = noteId) ?: return
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

    private fun deleteNoteFile(filePath: String?): Boolean {
        return diaryFileManager.deleteFile(filePath = filePath)
    }
}
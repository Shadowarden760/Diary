package com.homeapps.diary.ui.features.notedetail

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.domain.usecases.notes.GetNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.UpdateNoteUseCase
import com.homeapps.diary.utils.DiaryFileManager
import com.homeapps.diary.utils.DiaryShareManager
import com.homeapps.diary.utils.DiarySnackBarManager

class NoteDetailViewModel(
    private val appContext: Context,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
): ViewModel() {
    private val diaryFileManager = DiaryFileManager(appContext)
    private val diaryShareManager = DiaryShareManager()

    fun getCurrentNote(noteId: Long): NoteData? = getNoteByIdUseCase(noteId = noteId)

    fun saveUpdatedNote(updatedNote: NoteData): Long = updateNoteUseCase(updatedNote)

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
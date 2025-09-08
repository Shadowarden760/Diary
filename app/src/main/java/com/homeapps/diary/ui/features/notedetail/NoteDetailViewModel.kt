package com.homeapps.diary.ui.features.notedetail

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.domain.usecases.notes.GetNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.UpdateNoteUseCase
import com.homeapps.diary.utils.DiaryFileManager
import com.homeapps.diary.utils.DiarySnackBarManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailViewModel(
    private val appContext: Context,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
): ViewModel() {
    private val diaryFileManager = DiaryFileManager(appContext)
    private val _state = MutableStateFlow<NoteDetailState>(NoteDetailState.Default)
    val state = _state.asStateFlow()

    fun getCurrentNote(noteId: Long) = viewModelScope.launch {
        val note = withContext(dispatcher) {
            getNoteByIdUseCase(noteId = noteId)
        }
        if (note == null) {
            _state.value = NoteDetailState.Error
        } else {
            _state.value = NoteDetailState.CurrentNote(note)
        }
    }

    fun saveUpdatedNote(updatedNote: NoteData) = CoroutineScope(dispatcher).launch {
        updateNoteUseCase(updatedNote)
    }

    fun hasStoragePermissions()= diaryFileManager.hasStoragePermissions()

    fun requestStoragePermissions(launcher: ActivityResultLauncher<Array<String>>) {
        launcher.launch(diaryFileManager.storagePermissions)
    }

    fun saveNoteToFile(noteId: Long, snackBarManager: DiarySnackBarManager) = viewModelScope.launch {
        val note = withContext(dispatcher) {
            getNoteByIdUseCase(noteId = noteId)
        }
        note?.let {
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
    }

    private fun deleteNoteFile(filePath: String?): Boolean {
        return diaryFileManager.deleteFile(filePath = filePath)
    }

    sealed class NoteDetailState {
        object Default: NoteDetailState()
        object Error: NoteDetailState()
        data class CurrentNote(val note: NoteData): NoteDetailState()
    }
}
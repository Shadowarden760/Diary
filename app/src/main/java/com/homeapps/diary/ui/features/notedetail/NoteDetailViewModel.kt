package com.homeapps.diary.ui.features.notedetail

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.R
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.notes.GetNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.UpdateNoteUseCase
import com.homeapps.diary.ui.BaseViewModel
import com.homeapps.diary.utils.DiaryFileManager
import com.homeapps.diary.utils.DiarySnackBarManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailViewModel(
    appContext: Context,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    createLogMessageUseCase: CreateLogMessageUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
): BaseViewModel(
    appContext = appContext,
    createLogMessageUseCase = createLogMessageUseCase,
    getLogMessagesUseCase = null,
) {
    private val diaryFileManager = DiaryFileManager(appContext = getAppContext())
    val state: StateFlow<NoteDetailState>
        field = MutableStateFlow<NoteDetailState>(NoteDetailState.Default)

    fun updateState(newState: NoteDetailState) {
        state.value = newState
    }

    fun getCurrentNote(noteId: Long) = viewModelScope.launch {
        val note = withContext(dispatcher) { getNoteByIdUseCase(noteId = noteId) }
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${NoteDetailViewModel::class.java}: getting current note $note"
        )
        if (note == null) {
            updateState(newState = NoteDetailState.Error)
        } else {
            updateState(newState = NoteDetailState.CurrentNote(note))
        }
    }

    fun saveUpdatedNote(updatedNote: NoteData) = CoroutineScope(dispatcher).launch {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${NoteDetailViewModel::class.java}: saving note $updatedNote"
        )
        updateNoteUseCase(updatedNote)
    }

    fun hasStoragePermissions()= diaryFileManager.hasStoragePermissions()

    fun requestStoragePermissions(launcher: ActivityResultLauncher<Array<String>>) {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${NoteDetailViewModel::class.java}: requesting storage permission..."
        )
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
            if (result.first) {
                message = getAppContext().getString(R.string.note_detail_text_file_was_saved)
                actionLabel = getAppContext().getString(R.string.note_detail_text_file_was_saved_cancellation)
                action = { deleteNoteFile(result.second) }
            } else {
                message = getAppContext().getString(R.string.note_detail_text_file_was_not_saved)
            }
            createLogMessage(
                logLevel = LoggingRepository.LogLevel.INFO,
                logMessage = "${NoteDetailViewModel::class.java}: saving note to file result - $message"
            )
            snackBarManager.showSnackBar(
                message = message,
                actionLabel = actionLabel,
                action = action
            )
        }
    }

    private fun deleteNoteFile(filePath: String?): Boolean {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${NoteDetailViewModel::class.java}: deleting note file $filePath"
        )
        return diaryFileManager.deleteFile(filePath = filePath)
    }

    sealed class NoteDetailState {
        object Default: NoteDetailState()
        object Error: NoteDetailState()
        data class CurrentNote(val note: NoteData): NoteDetailState()
    }
}
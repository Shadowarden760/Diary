package com.homeapps.diary.ui.features.notelist

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.notes.CreateNewNoteUseCase
import com.homeapps.diary.domain.usecases.notes.DeleteNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNotesFlowUseCase
import com.homeapps.diary.domain.usecases.notes.UpdateNoteUseCase
import com.homeapps.diary.ui.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListViewModel(
    appContext: Context,
    private val createNewNoteUseCase: CreateNewNoteUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    getNotesFlowUseCase: GetNotesFlowUseCase,
    createLogMessageUseCase: CreateLogMessageUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
): BaseViewModel(
    appContext = appContext,
    createLogMessageUseCase = createLogMessageUseCase,
    getLogMessagesUseCase = null,
) {
    val notesFlow = getNotesFlowUseCase().distinctUntilChanged()

    fun createNewNote(goToNote: (Long) -> Unit) = viewModelScope.launch {
        val newNoteId = withContext(dispatcher) { createNewNoteUseCase() }
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${NoteListViewModel::class.java}: creating new note with id $newNoteId"
        )
        goToNote(newNoteId)
    }

    fun updateNoteOrder(orderedNotes: List<NoteData>) = viewModelScope.launch {
        orderedNotes.forEach { updateNoteUseCase(note = it, updateTime = false) }
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${NoteListViewModel::class.java}: updating note order"
        )
    }

    fun deleteNote(noteId: Long) = CoroutineScope(dispatcher).launch {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${NoteListViewModel::class.java}: deleting note with id $noteId"
        )
        deleteNoteByIdUseCase(noteId = noteId)
    }
}
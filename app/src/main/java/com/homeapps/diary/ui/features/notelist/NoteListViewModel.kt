package com.homeapps.diary.ui.features.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.domain.usecases.notes.CreateNewNoteUseCase
import com.homeapps.diary.domain.usecases.notes.DeleteNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNotesFlowUseCase
import com.homeapps.diary.domain.usecases.notes.UpdateNoteUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListViewModel(
    private val createNewNoteUseCase: CreateNewNoteUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    getNotesFlowUseCase: GetNotesFlowUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
): ViewModel() {
    val notesFlow = getNotesFlowUseCase().distinctUntilChanged()

    fun createNewNote(goToNote: (Long) -> Unit) = viewModelScope.launch {
        val newNoteId = withContext(dispatcher) {
            createNewNoteUseCase()
        }
        goToNote(newNoteId)
    }

    fun updateNoteOrder(orderedNotes: List<NoteData>) = viewModelScope.launch {
        orderedNotes.forEach { updateNoteUseCase(note = it, updateTime = false) }
    }

    fun deleteNote(noteId: Long) = CoroutineScope(dispatcher).launch {
        deleteNoteByIdUseCase(noteId = noteId)
    }
}
package com.homeapps.diary.ui.features.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.usecases.notes.CreateNewNoteUseCase
import com.homeapps.diary.domain.usecases.notes.DeleteNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNotesFlowUseCase
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val createNewNoteUseCase: CreateNewNoteUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    getNotesFlowUseCase: GetNotesFlowUseCase,
): ViewModel() {
    val notesFlow = getNotesFlowUseCase()

    fun createNewNote(goToNote: (Long) -> Unit) = viewModelScope.launch {
        val newNoteId = createNewNoteUseCase()
        goToNote(newNoteId)
    }

    fun deleteNote(noteId: Long) = viewModelScope.launch {
        deleteNoteByIdUseCase(noteId = noteId)
    }
}
package com.homeapps.diary.ui.features.notelist

import androidx.lifecycle.ViewModel
import com.homeapps.diary.domain.usecases.notes.CreateNewNoteUseCase
import com.homeapps.diary.domain.usecases.notes.DeleteNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNotesFlowUseCase

class NoteListViewModel(
    private val createNewNoteUseCase: CreateNewNoteUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    getNotesFlowUseCase: GetNotesFlowUseCase,
): ViewModel() {
    val notesFlow = getNotesFlowUseCase()

    fun createNewNote(): Long = createNewNoteUseCase()

    fun deleteNote(noteId: Long) = deleteNoteByIdUseCase(noteId = noteId)
}
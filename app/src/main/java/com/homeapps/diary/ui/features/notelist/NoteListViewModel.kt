package com.homeapps.diary.ui.features.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.usecases.notes.CreateNewNoteUseCase
import com.homeapps.diary.domain.usecases.notes.DeleteNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNotesFlowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val createNewNoteUseCase: CreateNewNoteUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    getNotesFlowUseCase: GetNotesFlowUseCase,
): ViewModel() {
    private val _state = MutableStateFlow<NoteListState>(NoteListState.Default)
    val state = _state.asStateFlow()
    val notesFlow = getNotesFlowUseCase()

    fun updateState(newState: NoteListState) {
        _state.value = NoteListState.Default
    }

    fun createNewNote() = viewModelScope.launch {
        val newNoteId = createNewNoteUseCase()
        _state.value = NoteListState.NewNote(noteId = newNoteId)
    }

    fun deleteNote(noteId: Long) = viewModelScope.launch {
        deleteNoteByIdUseCase(noteId = noteId)
    }

    sealed class NoteListState {
        object Default: NoteListState()
        data class NewNote(val noteId: Long): NoteListState()
    }
}
package com.homeapps.diary.ui.features.notelist

import androidx.lifecycle.ViewModel
import com.homeapps.diary.data.repositories.NotesRepository

class NoteListViewModel(private val notesRepository: NotesRepository): ViewModel() {
    val notesFlow = notesRepository.getUserNotesFlow()

    fun createNewNote() = notesRepository.createNewNote()

    fun deleteNote(noteId: Long) = notesRepository.deleteNote(noteId)
}
package com.specialtech.diary.ui.features.notelist

import androidx.lifecycle.ViewModel
import com.specialtech.diary.data.repositories.NotesRepository

class NoteListViewModel(private val notesRepository: NotesRepository): ViewModel() {
    val notesFlow = notesRepository.getUserNotesFlow()

    fun createNewNote() = notesRepository.createNewNote()

    fun deleteNote(noteId: Long) = notesRepository.deleteNote(noteId)

}
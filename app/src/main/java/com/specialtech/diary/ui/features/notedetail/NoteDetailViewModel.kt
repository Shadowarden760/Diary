package com.specialtech.diary.ui.features.notedetail

import androidx.lifecycle.ViewModel
import com.specialtech.diary.Note
import com.specialtech.diary.data.repositories.NotesRepository

class NoteDetailViewModel(private val notesRepository: NotesRepository): ViewModel() {

    fun getCurrentNote(noteId: Long) = notesRepository.getUserNoteById(noteId = noteId)

    fun saveUpdatedNote(updatedNote: Note) {
        notesRepository.updateNote(updatedNote)
    }

}
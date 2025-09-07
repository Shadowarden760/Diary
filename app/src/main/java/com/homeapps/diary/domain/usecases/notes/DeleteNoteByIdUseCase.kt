package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesRepository

class DeleteNoteByIdUseCase(private val notesRepository: NotesRepository) {

    operator fun invoke(noteId: Long) {
        notesRepository.deleteNoteById(noteId = noteId)
    }
}
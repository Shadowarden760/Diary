package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesRepository

class CreateNewNoteUseCase(private val notesRepository: NotesRepository) {

    operator fun invoke(): Long {
        return notesRepository.createNewNote()
    }
}
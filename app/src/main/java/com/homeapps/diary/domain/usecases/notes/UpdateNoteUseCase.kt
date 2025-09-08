package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesRepository
import com.homeapps.diary.domain.models.notes.NoteData


class UpdateNoteUseCase(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: NoteData): Long {
        return notesRepository.updateNote(note = note)
    }
}
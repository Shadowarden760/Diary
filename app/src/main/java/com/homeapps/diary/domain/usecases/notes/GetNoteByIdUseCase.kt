package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesRepository
import com.homeapps.diary.domain.models.notes.NoteData

class GetNoteByIdUseCase(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(noteId: Long): NoteData? {
        return notesRepository.getNoteById(noteId = noteId)
    }
}
package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesRepository
import com.homeapps.diary.domain.models.notes.NoteData


class UpdateNoteUseCase(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: NoteData, updateTime: Boolean = true): Long {
        return notesRepository.updateNote(note = note, updateTime = updateTime)
    }
}
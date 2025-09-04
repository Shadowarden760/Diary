package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesDataApi
import com.homeapps.diary.domain.models.notes.NoteData


class UpdateNoteUseCase(private val notesDataApi: NotesDataApi) {

    operator fun invoke(note: NoteData): Long {
        return notesDataApi.updateNote(note = note)
    }
}
package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesDataApi
import com.homeapps.diary.domain.models.notes.NoteData

class GetNoteByIdUseCase(private val notesDataApi: NotesDataApi) {

    operator fun invoke(noteId: Long): NoteData? {
        return notesDataApi.getNoteById(noteId = noteId)
    }
}
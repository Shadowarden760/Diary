package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesDataApi

class DeleteNoteByIdUseCase(private val notesDataApi: NotesDataApi) {

    operator fun invoke(noteId: Long) {
        notesDataApi.deleteNoteById(noteId = noteId)
    }
}
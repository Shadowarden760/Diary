package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesDataApi

class CreateNewNoteUseCase(private val notesDataApi: NotesDataApi) {

    operator fun invoke(): Long {
        return notesDataApi.createNewNote()
    }
}
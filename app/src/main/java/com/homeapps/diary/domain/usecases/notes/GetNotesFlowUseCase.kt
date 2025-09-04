package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesDataApi
import com.homeapps.diary.domain.models.notes.NoteData
import kotlinx.coroutines.flow.Flow

class GetNotesFlowUseCase(private val notesDataApi: NotesDataApi) {

    operator fun invoke(): Flow<List<NoteData>> {
        return notesDataApi.getAllNotesFlow()
    }
}
package com.homeapps.diary.domain.usecases.notes

import com.homeapps.diary.domain.api.NotesRepository
import com.homeapps.diary.domain.models.notes.NoteData
import kotlinx.coroutines.flow.Flow

class GetNotesFlowUseCase(private val notesRepository: NotesRepository) {

    operator fun invoke(): Flow<List<NoteData>> {
        return notesRepository.getAllNotesFlow()
    }
}
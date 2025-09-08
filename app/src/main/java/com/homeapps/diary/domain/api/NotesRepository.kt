package com.homeapps.diary.domain.api

import com.homeapps.diary.domain.models.notes.NoteData
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun createNewNote(): Long

    suspend fun getAllNotes(): List<NoteData>

    fun getAllNotesFlow(): Flow<List<NoteData>>

    suspend fun getNoteById(noteId: Long): NoteData?

    suspend fun updateNote(note: NoteData): Long

    suspend fun deleteNoteById(noteId: Long)
}
package com.homeapps.diary.domain.api

import com.homeapps.diary.domain.models.notes.NoteData
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun createNewNote(): Long

    fun getAllNotes(): List<NoteData>

    fun getAllNotesFlow(): Flow<List<NoteData>>

    fun getNoteById(noteId: Long): NoteData?

    fun getNoteByIdFlow(noteId: Long): Flow<NoteData?>

    fun updateNote(note: NoteData): Long

    fun deleteNoteById(noteId: Long)
}
package com.specialtech.diary.data.datasources.notes

import com.specialtech.diary.Note
import kotlinx.coroutines.flow.Flow

interface NotesDataSource {

    fun createNewNote(): Long

    fun getAllNotes(): List<Note>

    fun getAllNotesFlow(): Flow<List<Note>>

    fun getNoteById(noteId: Long): Note

    fun getNoteByIdFlow(noteId: Long): Flow<Note?>

    fun updateNote(note: Note): Long

    fun deleteNoteById(noteId: Long)

}
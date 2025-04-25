package com.specialtech.diary.data.datasources.notes

import com.specialtech.diary.Note
import kotlinx.coroutines.flow.Flow

interface NotesDataSource {

    fun getAllNotes(): List<Note>

    fun getAllNotesFlow(): Flow<List<Note>>

    fun getNoteById(noteId: Long): Note?

    fun createNewNote(): Long

    fun updateNote(note: Note): Long

    fun deleteNoteById(noteId: Long)

}
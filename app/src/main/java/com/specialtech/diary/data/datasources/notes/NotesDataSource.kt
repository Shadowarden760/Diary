package com.specialtech.diary.data.datasources.notes

import com.specialtech.diary.Note

interface NotesDataSource {

    fun getAllNotes(): List<Note>

    fun getNoteById(noteId: Long): Note?

    fun insertNote(newNote: Note): Long

    fun deleteNoteById(noteId: Long)

}
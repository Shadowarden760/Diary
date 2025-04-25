package com.specialtech.diary.data.repositories

import com.specialtech.diary.data.datasources.notes.NotesDataSource

class NotesRepository(private val notesDataSource: NotesDataSource) {

    fun getUserNotes() = notesDataSource.getAllNotes()

    fun getUserNotesFlow() = notesDataSource.getAllNotesFlow()

    fun getUserNoteById(noteId: Long) = notesDataSource.getNoteById(noteId)

    fun createNewNote() = notesDataSource.createNewNote()

    fun deleteNote(noteId: Long) = notesDataSource.deleteNoteById(noteId)

}
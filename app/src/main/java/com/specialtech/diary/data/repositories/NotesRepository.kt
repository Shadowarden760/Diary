package com.specialtech.diary.data.repositories

import com.specialtech.diary.Note
import com.specialtech.diary.data.datasources.notes.NotesDataSource

class NotesRepository(private val notesDataSource: NotesDataSource) {

    fun createNewNote() = notesDataSource.createNewNote()

    fun getUserNotes() = notesDataSource.getAllNotes()

    fun getUserNotesFlow() = notesDataSource.getAllNotesFlow()

    fun getUserNoteById(noteId: Long) = notesDataSource.getNoteById(noteId)

    fun getUserNoteByIdFlow(noteId: Long) = notesDataSource.getNoteByIdFlow(noteId)

    fun updateNote(note: Note) = notesDataSource.updateNote(note)

    fun deleteNote(noteId: Long) = notesDataSource.deleteNoteById(noteId)

}
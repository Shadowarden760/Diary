package com.specialtech.diary.data.datasources.notes.local

import com.specialtech.diary.DiaryDB
import com.specialtech.diary.Note
import com.specialtech.diary.data.datasources.database.DatabaseDriver
import com.specialtech.diary.data.datasources.notes.NotesDataSource

class NotesDatabaseData(databaseDriver: DatabaseDriver): NotesDataSource {
    private val database = DiaryDB(databaseDriver.createDatabaseDriver())
    private val queries = database.noteDatabaseQueries

    override fun getAllNotes(): List<Note> {
        return queries.getAllNotes()
            .executeAsList()
    }

    override fun getNoteById(noteId: Long): Note? {
        return queries.getNoteById(noteId)
            .executeAsOneOrNull()
    }

    override fun insertNote(newNote: Note): Long {
        queries.insertNote(
            noteId = newNote.noteId,
            noteCategory = newNote.noteCategory,
            noteTitle = newNote.noteTitle,
            noteMessage = newNote.noteMessage
        )
        return queries.lastInsertedId()
            .executeAsOne()
    }

    override fun deleteNoteById(noteId: Long) {
        return queries.deleteNoteById(noteId)
    }

}
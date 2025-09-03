package com.homeapps.diary.data.datasources.notes

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.homeapps.diary.DiaryDB
import com.homeapps.diary.Note
import com.homeapps.diary.data.clients.DatabaseDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NotesDatabaseDao(databaseDriver: DatabaseDriver): NotesDataSource {
    private val database = DiaryDB.Companion(databaseDriver.createDatabaseDriver())
    private val queries = database.noteDatabaseQueries

    override fun createNewNote(): Long {
        queries.insertNewNote(
            noteTitle = "",
            noteMessage = "",
            noteCreatedAt = System.currentTimeMillis(),
            noteUpdatedAt = System.currentTimeMillis()
        )
        return queries.lastInsertedId().executeAsOne()
    }

    override fun getAllNotes(): List<Note> {
        return queries.getAllNotes().executeAsList()
    }

    override fun getAllNotesFlow(): Flow<List<Note>> {
        return queries.getAllNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override fun getNoteById(noteId: Long): Note? {
        return queries.getNoteById(noteId).executeAsOneOrNull()
    }

    override fun getNoteByIdFlow(noteId: Long): Flow<Note?> {
        return queries.getNoteById(noteId)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
    }


    override fun updateNote(note: Note): Long {
        queries.updateNote(
            noteId = note.noteId,
            noteTitle = note.noteTitle,
            noteMessage = note.noteMessage,
            noteCreatedAt = note.noteCreatedAt,
            noteUpdatedAt = System.currentTimeMillis()
        )
        return note.noteId
    }

    override fun deleteNoteById(noteId: Long) {
        queries.deleteNoteById(noteId)
    }
}
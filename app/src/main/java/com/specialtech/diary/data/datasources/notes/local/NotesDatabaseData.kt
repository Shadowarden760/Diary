package com.specialtech.diary.data.datasources.notes.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.specialtech.diary.DiaryDB
import com.specialtech.diary.Note
import com.specialtech.diary.data.datasources.database.DatabaseDriver
import com.specialtech.diary.data.datasources.notes.NotesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NotesDatabaseData(databaseDriver: DatabaseDriver): NotesDataSource {
    private val database = DiaryDB(databaseDriver.createDatabaseDriver())
    private val queries = database.noteDatabaseQueries

    override fun createNewNote(): Long {
        queries.insertNewNote(
            noteTitle = "",
            noteMessage = "",
            noteCreatedAt = System.currentTimeMillis(),
            noteUpdatedAt = System.currentTimeMillis()
        )
        return queries.lastInsertedId()
            .executeAsOne()
    }

    override fun getAllNotes(): List<Note> {
        return queries.getAllNotes()
            .executeAsList()
    }

    override fun getAllNotesFlow(): Flow<List<Note>> {
        return queries.getAllNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override fun getNoteById(noteId: Long): Note {
        return queries.getNoteById(noteId)
            .executeAsOne()
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
        return queries.deleteNoteById(noteId)
    }

}
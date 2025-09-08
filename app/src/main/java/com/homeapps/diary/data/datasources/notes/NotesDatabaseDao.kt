package com.homeapps.diary.data.datasources.notes

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.homeapps.diary.DiaryDB
import com.homeapps.diary.NoteDBO
import com.homeapps.diary.data.clients.DatabaseDriver
import com.homeapps.diary.domain.models.notes.NoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NotesDatabaseDao(databaseDriver: DatabaseDriver) {
    private val database = DiaryDB.Companion(databaseDriver.createDatabaseDriver())
    private val queries = database.noteDBOQueries

    suspend fun createNewNote(): Long {
        queries.insertNewNote(
            noteTitle = "",
            noteMessage = "",
            noteCreatedAt = System.currentTimeMillis(),
            noteUpdatedAt = System.currentTimeMillis()
        )
        return queries.lastInsertedNoteId().awaitAsOne()
    }

    suspend fun getAllNotes(): List<NoteDBO> {
        return queries.getAllNotes().awaitAsList()
    }

    fun getAllNotesFlow(): Flow<List<NoteDBO>> {
        return queries.getAllNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    suspend fun getNoteById(noteId: Long): NoteDBO? {
        return queries.getNoteById(noteId).awaitAsOneOrNull()
    }


    suspend fun updateNote(note: NoteData): Long {
        queries.updateNote(
            noteId = note.noteId,
            noteTitle = note.noteTitle,
            noteMessage = note.noteMessage,
            noteCreatedAt = note.noteCreatedAt,
            noteUpdatedAt = System.currentTimeMillis()
        )
        return note.noteId
    }

    suspend fun deleteNoteById(noteId: Long) {
        queries.deleteNoteById(noteId)
    }
}
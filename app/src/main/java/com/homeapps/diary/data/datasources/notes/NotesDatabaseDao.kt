package com.homeapps.diary.data.datasources.notes

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.homeapps.diary.DiaryDB
import com.homeapps.diary.data.clients.DatabaseDriver
import com.homeapps.diary.data.mappers.toNoteData
import com.homeapps.diary.domain.models.notes.NoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesDatabaseDao(databaseDriver: DatabaseDriver) {
    private val database = DiaryDB.Companion(databaseDriver.createDatabaseDriver())
    private val queries = database.noteDatabaseQueries

    fun createNewNote(): Long {
        queries.insertNewNote(
            noteTitle = "",
            noteMessage = "",
            noteCreatedAt = System.currentTimeMillis(),
            noteUpdatedAt = System.currentTimeMillis()
        )
        return queries.lastInsertedId().executeAsOne()
    }

    fun getAllNotes(): List<NoteData> {
        return queries.getAllNotes().executeAsList().map { it.toNoteData() }
    }

    fun getAllNotesFlow(): Flow<List<NoteData>> {
        return queries.getAllNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { noteDBOS ->
                val noteData = mutableListOf<NoteData>()
                noteDBOS.forEach { noteData.add(it.toNoteData()) }
                noteData
            }
    }

    fun getNoteById(noteId: Long): NoteData? {
        return queries.getNoteById(noteId).executeAsOneOrNull()?.toNoteData()
    }

    fun getNoteByIdFlow(noteId: Long): Flow<NoteData?> {
        return queries.getNoteById(noteId)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { noteDBO -> noteDBO?.toNoteData() }
    }


    fun updateNote(note: NoteData): Long {
        queries.updateNote(
            noteId = note.noteId,
            noteTitle = note.noteTitle,
            noteMessage = note.noteMessage,
            noteCreatedAt = note.noteCreatedAt,
            noteUpdatedAt = System.currentTimeMillis()
        )
        return note.noteId
    }

    fun deleteNoteById(noteId: Long) {
        queries.deleteNoteById(noteId)
    }
}
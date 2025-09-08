package com.homeapps.diary.data.repositories

import com.homeapps.diary.data.datasources.notes.NotesDatabaseDao
import com.homeapps.diary.data.mappers.toNoteData
import com.homeapps.diary.domain.api.NotesRepository
import com.homeapps.diary.domain.models.notes.NoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(private val dao: NotesDatabaseDao): NotesRepository {

    override suspend fun createNewNote(): Long = dao.createNewNote()

    override suspend fun getAllNotes(): List<NoteData> = dao.getAllNotes().map { it.toNoteData() }

    override fun getAllNotesFlow(): Flow<List<NoteData>> = dao.getAllNotesFlow().map { it.map { dbo -> dbo.toNoteData() } }

    override suspend fun getNoteById(noteId: Long): NoteData? = dao.getNoteById(noteId)?.toNoteData()

    override suspend fun updateNote(note: NoteData): Long = dao.updateNote(note)

    override suspend fun deleteNoteById(noteId: Long): Unit = dao.deleteNoteById(noteId)
}
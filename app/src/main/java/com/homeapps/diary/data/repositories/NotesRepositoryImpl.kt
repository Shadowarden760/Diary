package com.homeapps.diary.data.repositories

import com.homeapps.diary.data.datasources.notes.NotesDatabaseDao
import com.homeapps.diary.domain.api.NotesRepository
import com.homeapps.diary.domain.models.notes.NoteData
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(private val dao: NotesDatabaseDao): NotesRepository {

    override fun createNewNote(): Long = dao.createNewNote()

    override fun getAllNotes(): List<NoteData> = dao.getAllNotes()

    override fun getAllNotesFlow(): Flow<List<NoteData>> = dao.getAllNotesFlow()

    override fun getNoteById(noteId: Long): NoteData? = dao.getNoteById(noteId)

    override fun getNoteByIdFlow(noteId: Long): Flow<NoteData?> = dao.getNoteByIdFlow(noteId)

    override fun updateNote(note: NoteData): Long = dao.updateNote(note)

    override fun deleteNoteById(noteId: Long): Unit = dao.deleteNoteById(noteId)
}
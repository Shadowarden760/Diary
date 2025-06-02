package com.specialtech.diary.ui.features.notedetail

import androidx.compose.runtime.Composable
import com.specialtech.diary.Note
import com.specialtech.diary.ui.features.notedetail.components.NoteData
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteDetailScreen(
    viewModel: NoteDetailViewModel = koinViewModel(),
    noteId: Long,
    goToNoteList: () -> Unit
) {
    val currentNote = viewModel.getCurrentNote(noteId = noteId)

    NoteData(
        noteData = currentNote,
        updateNote = { newTitle: String, newMessage: String ->
            val updatedNote = Note(
                noteId = currentNote.noteId,
                noteTitle = newTitle,
                noteMessage = newMessage,
                noteUpdatedAt = currentNote.noteUpdatedAt,
                noteCreatedAt = currentNote.noteCreatedAt
            )
            viewModel.saveUpdatedNote(updatedNote)
        }
    )
}
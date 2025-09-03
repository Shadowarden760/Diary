package com.homeapps.diary.ui.features.notelist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.diary.ui.features.notelist.components.NoteList
import com.homeapps.diary.ui.features.notelist.components.NoteListEnd
import com.homeapps.diary.ui.features.notelist.components.NoteListHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = koinViewModel(),
    goToNoteDetail:(noteId: Long) -> Unit = {}
) {
    val notes = viewModel.notesFlow.collectAsStateWithLifecycle(listOf())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NoteListHeader(noteNumber = notes.value.size)
        NoteList(
            noteList = notes.value,
            goToNoteDetail = goToNoteDetail,
            deleteNote = { noteId -> viewModel.deleteNote(noteId) }
        )
    }
    NoteListEnd(
        goToNoteDetail = goToNoteDetail,
        createNewNote = { viewModel.createNewNote() }
    )
}
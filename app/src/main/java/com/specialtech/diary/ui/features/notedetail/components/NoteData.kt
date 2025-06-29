package com.specialtech.diary.ui.features.notedetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.specialtech.diary.Note
import com.specialtech.diary.R
import com.specialtech.diary.ui.features.components.DiaryTextField


@Composable
fun NoteData(
    noteData: Note,
    updateNote: (newTitle: String, newMessage: String) -> Unit
) {
    val title = remember { mutableStateOf(noteData.noteTitle) }
    val message = remember { mutableStateOf(noteData.noteMessage) }

    Column(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        DiaryTextField(
            value = title.value,
            textStyle = MaterialTheme.typography.titleLarge,
            placeholder = {
                Text(
                    text = stringResource(R.string.note_detail_text_enter_your_title),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            onValueChange = { newTitle ->
                title.value = newTitle
                updateNote(title.value, message.value)
            },
            modifier = Modifier.fillMaxWidth()
        )
        DiaryTextField(
            value = message.value,
            textStyle = MaterialTheme.typography.titleMedium,
            placeholder = {
                Text(
                    text = stringResource(R.string.note_detail_text_enter_your_message),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default
            ),
            onValueChange = { newMessage ->
                message.value = newMessage
                updateNote(title.value, message.value)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun NoteDataPreview() {
    NoteData(
        noteData = Note(
            noteId = 0,
            noteTitle = "Title",
            noteMessage = "Veeeeeeeeeeeeery loooooooooooong meeeeeeeeeeeeeeessage",
            noteUpdatedAt = 0L,
            noteCreatedAt = 0L
        ),
        updateNote = { _, _ -> }
    )
}
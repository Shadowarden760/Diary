package com.homeapps.diary.ui.features.notedetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.ui.features.components.DiaryTextField
import com.homeapps.diary.utils.Utils.countTextCharacters
import com.homeapps.diary.utils.Utils.countTextWords

@Composable
fun NoteDataScreen(
    noteData: NoteData,
    updateNote: (newTitle: String, newMessage: String) -> Unit,
) {
    val title = remember { mutableStateOf(noteData.noteTitle) }
    val message = remember { mutableStateOf(noteData.noteMessage) }

    Column(modifier = Modifier
        .fillMaxSize()
        .imePadding()
        .padding(vertical = 16.dp, horizontal = 24.dp)
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
                capitalization = KeyboardCapitalization.Words,
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
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Default
            ),
            onValueChange = { newMessage ->
                message.value = newMessage
                updateNote(title.value, message.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.note_detail_text_characters, message.value.countTextCharacters()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp)
            )
            Text(
                text = stringResource(R.string.note_detail_text_words, message.value.countTextWords()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp)
            )
        }

    }
}

@Preview
@Composable
private fun NoteDataScreenPreview() {
    NoteDataScreen(
        noteData = NoteData(
            noteId = 0,
            noteTitle = "Title",
            noteMessage = "Veeeeeeeeeeeeery loooooooooooong meeeeeeeeeeeeeeessage",
            noteUpdatedAt = 0L,
            noteCreatedAt = 0L
        ),
        updateNote = { _, _ -> }
    )
}
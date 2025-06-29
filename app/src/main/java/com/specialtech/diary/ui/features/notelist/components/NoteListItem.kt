package com.specialtech.diary.ui.features.notelist.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.specialtech.diary.Note
import com.specialtech.diary.R
import com.specialtech.diary.utils.DateTimeUtils


@Composable
fun NoteListItem(
    note: Note,
    onItemClick:() -> Unit = {},
    onLongItemClick:() -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 2.dp),
        modifier = Modifier.combinedClickable(
            onClick = onItemClick,
            onLongClick = onLongItemClick
        )
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            Text(
                text = "${stringResource(R.string.note_list_text_note_number)}${note.noteId}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = note.noteTitle,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = DateTimeUtils.timeMillisToDate(note.noteUpdatedAt),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

@Preview
@Composable
private fun NoteListItemPreview() {
    NoteListItem(Note(0, "Title", "", 0, 1287371236786))
}
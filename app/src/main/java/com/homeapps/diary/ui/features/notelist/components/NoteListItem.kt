package com.homeapps.diary.ui.features.notelist.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.utils.DateTimeUtils

@Composable
fun NoteListItem(
    note: NoteData,
    onItemClick:() -> Unit = {},
    onLongItemClick:() -> Unit = {}
) {
    Card(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(),
        elevation = CardDefaults.outlinedCardElevation(),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier.combinedClickable(
            onClick = onItemClick,
            onLongClick = onLongItemClick
        )
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            Text(
                text = note.noteTitle,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = note.noteMessage,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
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
    NoteListItem(NoteData(0, "Title", "Very loooooooooooooooooooooooooooooooooooooooong text", 0, 1287371236786))
}
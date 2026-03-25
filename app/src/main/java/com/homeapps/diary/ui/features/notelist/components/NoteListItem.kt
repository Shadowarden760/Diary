package com.homeapps.diary.ui.features.notelist.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.ui.features.components.icons.DragHandle
import com.homeapps.diary.utils.DateTimeUtils

@Composable
fun NoteListItem(
    note: NoteData,
    onItemClick:() -> Unit = {},
    onLongItemClick:() -> Unit = {},
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
    Card(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(),
        elevation = CardDefaults.outlinedCardElevation(),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier
            .clip(CardDefaults.outlinedShape)
            .fillMaxSize()
            .combinedClickable(
                onClick = onItemClick,
                onLongClick = onLongItemClick,
                interactionSource = interactionSource
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(6.dp)) {
            IconButton(
                onClick = {},
                modifier = modifier.fillMaxWidth().height(20.dp)
            ) {
                Icon(
                    imageVector = DragHandle,
                    contentDescription = null
                )
            }
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
    NoteListItem(
        note = NoteData(
            noteId = 0,
            noteTitle = "Title",
            noteMessage = "Very loooooooooooooooooooooooooooooooooooooooong text",
            noteOrder = 0L,
            noteCreatedAt = 0,
            noteUpdatedAt = 1287371236786
        ),
        interactionSource = remember { MutableInteractionSource() }
    )
}
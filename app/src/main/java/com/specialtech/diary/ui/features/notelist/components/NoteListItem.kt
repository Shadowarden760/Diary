package com.specialtech.diary.ui.features.notelist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.specialtech.diary.Note
import com.specialtech.diary.ui.theme.MainOrange
import com.specialtech.diary.utils.DateTimeUtils


@Composable
fun NoteListItem(
    note: Note,
    onItemClick:() -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = MainOrange),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 2.dp),
        onClick = onItemClick
    ) {

        Column(modifier = Modifier.padding(6.dp)) {
            Text(
                text = "Note #${note.noteId}",
                fontWeight = FontWeight.Medium,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(6.dp)
            )

            Text(
                text = "Category: ${note.noteCategory}",
                fontWeight = FontWeight.Medium,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(6.dp)
            )

            Text(
                text = "Modified: ${DateTimeUtils.timeMillisToDate(note.noteUpdatedAt)}",
                fontWeight = FontWeight.Medium,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(6.dp)
            )
        }

    }
}

@Preview
@Composable
private fun NoteListItemPreview() {
    NoteListItem(Note(0, "category", "title", "", 0, 1287371236786))
}
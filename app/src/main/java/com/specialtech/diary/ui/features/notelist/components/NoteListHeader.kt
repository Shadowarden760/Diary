package com.specialtech.diary.ui.features.notelist.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.specialtech.diary.R


@Composable
fun NoteListHeader(
    noteNumber: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.note_list_text_your_notes),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.weight(1f))
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(disabledElevation = 5.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = noteNumber.toString(),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Preview
@Composable
private fun NoteListHeaderPreview() {
    NoteListHeader(5)
}
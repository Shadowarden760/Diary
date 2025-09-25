package com.homeapps.diary.ui.features.notedetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R

@Composable
fun NoteButtons(
    shareNote: () -> Unit,
    saveNote: () -> Unit,
    innerPadding: PaddingValues,
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(end = 24.dp, bottom = 16.dp)
        ) {
            FloatingActionButton(
                onClick = shareNote,
                shape = MaterialTheme.shapes.medium,
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null
                )
            }
            FloatingActionButton(
                onClick = saveNote,
                shape = MaterialTheme.shapes.medium,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_save),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview
private fun NoteButtonsPreview() {
    NoteButtons({}, {}, PaddingValues(16.dp))
}
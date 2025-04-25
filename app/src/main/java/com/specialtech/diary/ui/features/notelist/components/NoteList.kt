package com.specialtech.diary.ui.features.notelist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.specialtech.diary.Note

@Composable
fun NoteList(noteList: List<Note>)
{
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        items(noteList) { note ->
            NoteListItem(note)
        }
    }
}


@Preview
@Composable
private fun NoteListPreview() {
    NoteList(listOf(
        Note(1, "category", "title1", "",0,  1287371236786),
        Note(2, "category", "title2", "", 0,1287371236786),
        Note(3, "category", "title3", "", 0, 1287371236786),
        Note(4, "category", "title4", "", 0, 1287371236786)
    ))
}
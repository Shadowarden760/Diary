package com.homeapps.diary.domain.models.notes

data class NoteData(
    val noteId: Long,
    val noteTitle: String,
    val noteMessage: String,
    val noteOrder: Long,
    val noteCreatedAt: Long,
    val noteUpdatedAt: Long,
)
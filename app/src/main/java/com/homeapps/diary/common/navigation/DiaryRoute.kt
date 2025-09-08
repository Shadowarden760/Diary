package com.homeapps.diary.common.navigation

sealed class DiaryRoute(val route: String) {
    data object Home: DiaryRoute(route = "home")
    data object Alarm: DiaryRoute(route = "home_alarm")
    data object Weather: DiaryRoute(route = "weather")
    data object NoteList: DiaryRoute(route = "notes")
    data object NoteDetail: DiaryRoute(route = "notes_detail")

    companion object {
        const val NOTE_DETAIL_ARG_NOTE_ID = "noteId"
    }
}
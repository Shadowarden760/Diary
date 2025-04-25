package com.specialtech.diary.common.navigation

sealed class DiaryRoute(val route: String) {
    data object Home: DiaryRoute(route = "home")
    data object Weather: DiaryRoute(route = "weather")
    data object NoteList: DiaryRoute(route = "note_list")
    data object NoteDetail: DiaryRoute(route = "note_detail")
}
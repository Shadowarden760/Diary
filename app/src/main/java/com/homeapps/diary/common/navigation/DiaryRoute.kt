package com.homeapps.diary.common.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.homeapps.diary.R

sealed class DiaryRoute(
    @param:StringRes val title: Int = 0,
    @param:DrawableRes val icon: Int = 0,
): NavKey {
    data object Home: DiaryRoute(title = R.string.section_home, icon = R.drawable.ic_home)
    data object Alarm: DiaryRoute()
    data object Weather: DiaryRoute(title = R.string.section_weather, icon = R.drawable.ic_weather)
    data object NoteList: DiaryRoute(title = R.string.section_notes, icon = R.drawable.ic_note)
    data class NoteDetail(val noteId: Long): DiaryRoute()
}
package com.homeapps.diary.ui.features.components.navigationbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.homeapps.diary.R
import com.homeapps.diary.common.navigation.DiaryRoute

sealed class NavigationBarSection(
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int,
    val route: String
) {
    data object Home: NavigationBarSection(
        title = R.string.section_home,
        icon = R.drawable.ic_home,
        route = DiaryRoute.Home.route
    )

    data object Notes: NavigationBarSection(
        title = R.string.section_notes,
        icon = R.drawable.ic_note,
        route = DiaryRoute.NoteList.route
    )

    data object Weather: NavigationBarSection(
        title = R.string.section_weather,
        icon = R.drawable.ic_weather,
        route = DiaryRoute.Weather.route
    )

    companion object {
        val sections = listOf(Home, Notes, Weather)
    }
}
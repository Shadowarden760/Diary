package com.specialtech.diary.common.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.specialtech.diary.R

sealed class NavigationBarSection(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    companion object {
        val sections = listOf(Home, Notes, Weather)
    }

    data object Home : NavigationBarSection(
        title = R.string.section_home,
        icon = R.drawable.ic_home,
        route = DiaryRoute.Home.route
    )

    data object Notes : NavigationBarSection(
        title = R.string.section_notes,
        icon = R.drawable.ic_note,
        route = DiaryRoute.NoteList.route
    )

    data object Weather : NavigationBarSection(
        title = R.string.section_weather,
        icon = R.drawable.ic_weather,
        route = DiaryRoute.Weather.route
    )

}
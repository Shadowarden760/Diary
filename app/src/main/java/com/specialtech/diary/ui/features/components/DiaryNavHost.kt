package com.specialtech.diary.ui.features.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.specialtech.diary.common.DiaryAppState
import com.specialtech.diary.common.navigation.DiaryRoute
import com.specialtech.diary.common.navigation.DiaryRoute.Companion.NOTE_DETAIL_ARG_NOTE_ID
import com.specialtech.diary.common.navigation.NavigationBarSection
import com.specialtech.diary.common.navigation.navigateSaved
import com.specialtech.diary.common.navigation.popUp
import com.specialtech.diary.ui.features.home.HomeScreen
import com.specialtech.diary.ui.features.notedetail.NoteDetailScreen
import com.specialtech.diary.ui.features.notelist.NoteListScreen
import com.specialtech.diary.ui.features.weather.WeatherScreen

@Composable
fun DiaryNavHost(appState: DiaryAppState, paddingValues: PaddingValues) {
    NavHost(
        navController = appState.navController,
        startDestination = NavigationBarSection.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        val goToNoteDetailFromNoteList:(noteId: Long) -> Unit = { noteId ->
            appState.navigateSaved(
                route = "${DiaryRoute.NoteDetail.route}/$noteId",
                popUp = DiaryRoute.NoteList.route
            )
        }

        val goBack: () -> Unit = {
            appState.popUp()
        }

        composable(DiaryRoute.Home.route) {
            HomeScreen()
        }

        composable(DiaryRoute.NoteList.route) {
            NoteListScreen(goToNoteDetail = goToNoteDetailFromNoteList)
        }

        composable(
            route = DiaryRoute.NoteDetail.route + "/{$NOTE_DETAIL_ARG_NOTE_ID}",
            arguments = listOf(navArgument(NOTE_DETAIL_ARG_NOTE_ID) { type = NavType.LongType })
        ) { stackEntry ->
            val noteId = stackEntry.arguments?.getLong(NOTE_DETAIL_ARG_NOTE_ID)
            NoteDetailScreen(
                noteId = noteId ?: 0L,
                goToNoteList = goBack
            )
        }

        composable(DiaryRoute.Weather.route) {
            WeatherScreen(goHome = goBack)
        }

    }

}
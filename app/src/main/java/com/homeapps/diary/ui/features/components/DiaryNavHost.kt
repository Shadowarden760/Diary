package com.homeapps.diary.ui.features.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.homeapps.diary.common.DiaryAppState
import com.homeapps.diary.common.navigation.DiaryRoute
import com.homeapps.diary.common.navigation.DiaryRoute.Companion.NOTE_DETAIL_ARG_NOTE_ID
import com.homeapps.diary.common.navigation.NavigationBarSection
import com.homeapps.diary.common.navigation.navigate
import com.homeapps.diary.common.navigation.popUp
import com.homeapps.diary.ui.features.home.HomeScreen
import com.homeapps.diary.ui.features.homealarm.AlarmScreen
import com.homeapps.diary.ui.features.notedetail.NoteDetailScreen
import com.homeapps.diary.ui.features.notelist.NoteListScreen
import com.homeapps.diary.ui.features.weather.WeatherScreen

@Composable
fun DiaryNavHost(appState: DiaryAppState) {
    NavHost(
        navController = appState.navController,
        startDestination = NavigationBarSection.Home.route
    ) {
        val goToNoteDetailFromNoteList:(noteId: Long) -> Unit = { noteId ->
            appState.navigate(
                route = "${DiaryRoute.NoteDetail.route}/$noteId"
            )
        }

        val goToAlarmFromHome: () -> Unit = {
            appState.navigate(route = DiaryRoute.Alarm.route)
        }

        val goBack: () -> Unit = {
            appState.popUp()
        }

        composable(DiaryRoute.Home.route) {
            HomeScreen(goToAlarmScreen = goToAlarmFromHome)
        }

        composable(route = DiaryRoute.Alarm.route) {
            AlarmScreen(goBack = goBack)
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
                snackBarManager = appState.snackBarManager,
                goToNoteList = goBack
            )
        }

        composable(DiaryRoute.Weather.route) {
            WeatherScreen(
                snackBarManager = appState.snackBarManager,
                goHome = goBack
            )
        }
    }
}
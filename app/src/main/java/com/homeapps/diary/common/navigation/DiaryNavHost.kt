package com.homeapps.diary.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.homeapps.diary.common.DiaryAppState
import com.homeapps.diary.ui.features.home.HomeScreen
import com.homeapps.diary.ui.features.homealarm.AlarmScreen
import com.homeapps.diary.ui.features.notedetail.NoteDetailScreen
import com.homeapps.diary.ui.features.notelist.NoteListScreen
import com.homeapps.diary.ui.features.weather.WeatherScreen

@Composable
fun DiaryNavHost(appState: DiaryAppState, innerPaddingValues: PaddingValues) {
    NavDisplay(
        backStack = appState.topLevelBackStack.bakStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = { appState.topLevelBackStack.removeLast() },
        entryProvider = entryProvider {
            val goBack: () -> Unit = {
                appState.topLevelBackStack.removeLast()
            }

            entry<DiaryRoute.Home> {
                HomeScreen(
                    goToAlarmScreen = { appState.topLevelBackStack.add(DiaryRoute.Alarm) },
                    innerPadding = innerPaddingValues,
                    animationState = appState.themeAnimationState
                )
            }

            entry<DiaryRoute.Alarm> {
                AlarmScreen(
                    goBack = goBack,
                    innerPadding = innerPaddingValues
                )
            }

            entry<DiaryRoute.NoteList> {
                NoteListScreen(
                    goToNoteDetail = { noteId ->
                        appState.topLevelBackStack.add(DiaryRoute.NoteDetail(noteId = noteId))
                    },
                    innerPadding = innerPaddingValues
                )
            }

            entry<DiaryRoute.NoteDetail> { key ->
                NoteDetailScreen(
                    noteId = key.noteId,
                    snackBarManager = appState.snackBarManager,
                    goToNoteList = goBack,
                    innerPadding = innerPaddingValues
                )
            }

            entry<DiaryRoute.Weather> {
                WeatherScreen(
                    snackBarManager = appState.snackBarManager,
                    goHome = goBack,
                    innerPadding = innerPaddingValues
                )
            }
        }
    )
}
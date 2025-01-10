package com.specialtech.diary.ui.features.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.specialtech.diary.common.DiaryAppState
import com.specialtech.diary.common.navigation.DiaryRoute
import com.specialtech.diary.common.navigation.NavigationBarSection
import com.specialtech.diary.common.navigation.navigateSaved
import com.specialtech.diary.common.navigation.popUp
import com.specialtech.diary.ui.features.home.HomeScreen
import com.specialtech.diary.ui.features.money.MoneyScreen
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
        val goToNoteDetailFromNoteList: () -> Unit = {
            appState.navigateSaved(
                route = DiaryRoute.NoteDetail.route,
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

        composable(DiaryRoute.NoteDetail.route) {
            NoteDetailScreen(goToNoteList = goBack)
        }

        composable(DiaryRoute.Weather.route) {
            WeatherScreen()
        }

        composable(DiaryRoute.Money.route) {
            MoneyScreen()
        }

    }
}
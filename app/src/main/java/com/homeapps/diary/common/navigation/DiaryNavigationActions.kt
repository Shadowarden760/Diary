package com.homeapps.diary.common.navigation

import com.homeapps.diary.common.DiaryAppState

fun DiaryAppState.popUp() {
    navController.popBackStack()
}

fun DiaryAppState.navigate(route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}

fun DiaryAppState.navigateAndPopUp(route: String, popUp: String) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}

fun DiaryAppState.navigateSaved(route: String, popUp: String) {
    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(popUp) { saveState = true }
    }
}

fun DiaryAppState.clearNavigate(route: String) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
    }
}
package com.homeapps.diary.common.navigation

import androidx.lifecycle.ViewModel

class NavViewModel: ViewModel() {
    private var topLevelBackStack: TopLevelBackStack<DiaryRoute> = TopLevelBackStack(
        startKey = DiaryRoute.Home,
        updateViewModel = ::updateBackStack
    )

    fun getBackStack(): TopLevelBackStack<DiaryRoute> = topLevelBackStack

    private fun updateBackStack(newBackStack: TopLevelBackStack<DiaryRoute>) {
        topLevelBackStack = newBackStack
    }
}
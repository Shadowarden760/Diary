package com.homeapps.diary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.homeapps.diary.common.navigation.DiaryRoute
import com.homeapps.diary.common.rememberAppState
import com.homeapps.diary.ui.features.components.DiaryNavHost
import com.homeapps.diary.ui.features.components.navigationbar.BottomBar
import com.homeapps.diary.ui.theme.DiaryTheme
import io.github.themeanimator.ThemeAnimationScope
import io.github.themeanimator.theme.isDark

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val bottomItems = listOf(DiaryRoute.Home, DiaryRoute.NoteList, DiaryRoute.Weather)
            val appState = rememberAppState()

            ThemeAnimationScope(state = appState.themeAnimationState) {
                DiaryTheme(darkTheme = appState.themeAnimationState.uiTheme.isDark()) {
                    Scaffold(
                        snackbarHost = { SnackbarHost(hostState = appState.snackBarManager.getHostState()) },
                        bottomBar = { BottomBar(appState = appState, bottomItems = bottomItems)},
                        contentWindowInsets = WindowInsets.safeDrawing,
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        DiaryNavHost(appState = appState, innerPaddingValues = innerPadding)
                    }
                }
            }
        }
    }
}

class TopLevelBackStack<T: NavKey> (private val startKey: T) {
    private var topLevelBackStacks: HashMap<T, SnapshotStateList<T>> = hashMapOf(
        startKey to mutableStateListOf(startKey)
    )

    var topLevelKey = mutableStateOf(startKey)
        private set

    val bakStack = mutableStateListOf(startKey)

    fun switchTopLevel(key: T) {
        if (topLevelBackStacks[key] == null) {
            topLevelBackStacks[key] = mutableStateListOf(key)
        }
        topLevelKey.value = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelBackStacks[topLevelKey.value]?.add(key)
        updateBackStack()
    }

    fun replaceStack(vararg keys: T) {
        topLevelBackStacks[topLevelKey.value] = mutableStateListOf(*keys)
        updateBackStack()
    }

    fun removeLast() {
        val currentStack = topLevelBackStacks[topLevelKey.value] ?: return
        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        } else if (topLevelKey.value != startKey) {
            topLevelKey.value = startKey
        }
        updateBackStack()
    }

    private fun updateBackStack() {
        bakStack.clear()
        val currentStack = topLevelBackStacks[topLevelKey.value] ?: emptyList()

        if (topLevelKey.value == startKey) {
            bakStack.addAll(currentStack)
        } else {
            val startStack = topLevelBackStacks[startKey] ?: emptyList()
            bakStack.addAll(startStack + currentStack)
        }
    }
}
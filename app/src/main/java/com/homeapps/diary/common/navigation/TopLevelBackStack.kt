package com.homeapps.diary.common.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

class TopLevelBackStack<T: NavKey>(
    private val startKey: T,
    private val updateViewModel: (TopLevelBackStack<T>) -> Unit
) {
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
        updateViewModel(this)
    }
}
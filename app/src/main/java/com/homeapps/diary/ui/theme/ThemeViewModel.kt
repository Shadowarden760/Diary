package com.homeapps.diary.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.usecases.settings.GetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.settings.SetDarkThemeUseCase
import io.github.themeanimator.theme.Theme
import io.github.themeanimator.theme.ThemeProvider
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ThemeViewModel(
    private val getDarkThemeUseCase: GetDarkThemeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase
): ViewModel(), ThemeProvider {

    override val currentTheme: StateFlow<Theme> = getDarkThemeUseCase().
        map { value ->
            if (value) Theme.Dark else Theme.Light
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Theme.Dark
        )

    override suspend fun updateTheme(theme: Theme) {
        setDarkThemeUseCase.invoke(darkTheme = theme is Theme.Dark)
    }
}
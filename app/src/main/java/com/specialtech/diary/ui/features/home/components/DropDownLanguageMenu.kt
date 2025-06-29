package com.specialtech.diary.ui.features.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.specialtech.diary.R
import com.specialtech.diary.utils.AppLanguage
import com.specialtech.diary.utils.appLanguages

@Composable
fun DropDownLanguageMenu(
    onItemClick: (language: AppLanguage) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expanded.value = true }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_language),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            appLanguages.forEach { language ->
                DropdownMenuItem(
                    onClick = { onItemClick(language) },
                    text = {
                        Text(
                            text = language.displayLanguage,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            }
        }
    }
}


@Preview
@Composable
fun DropDownLanguageMenuPreview() {
    DropDownLanguageMenu(onItemClick = {})
}
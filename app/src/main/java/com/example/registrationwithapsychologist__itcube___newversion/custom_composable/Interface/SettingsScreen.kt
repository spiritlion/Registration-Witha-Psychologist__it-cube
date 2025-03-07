package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val useSystemTheme = remember { mutableStateOf(true) }
    val darkThemeEnabled = remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            "Внимание, здесь пока что-то только визуальная часть. Эти настройки не на что не влияют",
            color = Color.Red
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Использовать системную тему")
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = useSystemTheme.value,
                onCheckedChange = { useSystemTheme.value = it }
            )
        }
        if (!useSystemTheme.value) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Темная тема")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = darkThemeEnabled.value,
                    onCheckedChange = { darkThemeEnabled.value = it }
                )
            }
        }
    }
}

package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.registrationwithapsychologist__itcube___newversion.DataStoreRepository
import com.example.registrationwithapsychologist__itcube___newversion.isTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun SettingsScreen(modifier: Modifier = Modifier, repository: DataStoreRepository) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            Row {
                Button(
                    {
                        GlobalScope.launch {
                            repository.saveInt(isTheme.intValue)
                        }
                    }
                ) {
                    Text("Сохранить")
                }
                Button(
                    {

                    }
                ) {
                    Text("Отмена")
                }
                Button(
                    {

                    }
                ) {
                    Text("Вернуть к базовым настройка")
                }
            }
        }
        /*
        item {
            Text(
                "Внимание, здесь пока что-то только визуальная часть. Эти настройки не на что не влияют",
                color = Color.Red
            )
        }
         */
        /*
        item {
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

                fun onCheckboxChanged(checked: Boolean) {
                    isTheme.intValue = if (!checked) 0 else 1
                    // Сохраняем новое значение в DataStore

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Тёмная тема")
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isTheme.intValue == 2,
                        onCheckedChange = {
                            onCheckboxChanged(it)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = Color.White,
                            checkedBorderColor = Color.Black,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.Black,
                            uncheckedBorderColor = Color.White
                        ),
                        thumbContent = {
                            if (isTheme.intValue == 2) {
                                Text("\uD83C\uDF18")
                            } else {
                                Text("☀\uFE0F")
                            }
                        }
                    )
                }
            }
        }
         */
        item {
            val options = listOf(0, 1, 2)
            var expanded by remember { mutableStateOf(false) }
            var currentTheme by remember { mutableStateOf(isTheme) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    // The `menuAnchor` modifier must be passed to the text field for correctness.
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = when (currentTheme.intValue) {
                        1 -> "Светлая"
                        2 -> "Темная"
                        else -> "Как в система"
                    },
                    onValueChange = {},
                    label = { Text("Пол") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    when (selectionOption) {
                                        1 -> "Светлая"
                                        2 -> "Тёмная"
                                        else -> "Как в системе"
                                    }
                                )
                            },
                            onClick = {
                                currentTheme.intValue = selectionOption
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
    }
}

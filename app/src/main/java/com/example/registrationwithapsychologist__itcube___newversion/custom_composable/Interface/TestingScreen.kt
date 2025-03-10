package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestingScreen(modifier: Modifier = Modifier) {
    Column {
        Text("Ещё не реализованные или ненужные функции")
        LazyColumn {
            item {
                var intermediateGender by remember { mutableStateOf(PersonData.Gender.Man) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Пол: "
                    )
                    Text("Мужской")
                    Switch(
                        checked = (intermediateGender == PersonData.Gender.Woman),
                        onCheckedChange = {
                            intermediateGender = if (it) {
                                PersonData.Gender.Woman
                            } else {
                                PersonData.Gender.Man
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = Color.Red,
                            checkedThumbColor = Color.White,
                            checkedBorderColor = Color.Red,
                            uncheckedTrackColor = Color.Blue,
                            uncheckedThumbColor = Color.White,
                            uncheckedBorderColor = Color.Blue

                        )
                    )
                    Text("Женский")
                }
            }
        }
    }
}
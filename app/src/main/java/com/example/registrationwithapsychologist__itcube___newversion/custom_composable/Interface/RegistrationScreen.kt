package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    var state by remember { mutableIntStateOf(1) }

    var mail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var patronymiс by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf(listOf(12, 10, 1915)) }
    var gender by remember { mutableStateOf(PersonData.Gender.Man) }
    var isAgree by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Регистрация", fontSize = 20.sp)
        when (state) {
            1 -> {
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Электроная почта") },
                    value = mail,
                    onValueChange = { mail = it },
                    placeholder = { Text("Введите вашу электронную почту") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Пароль") },
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Введите ваш пароль") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Подтвердите пароль") },
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    placeholder = { Text("Введите ваш пароль ещё раз") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    { if (mail != "" && password == currentPassword && password != "" ) state ++ }
                ) {
                    Text("Дальше")
                }
            }
            2 -> {
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Фамилия") },
                    value = surname,
                    onValueChange = { surname = it },
                    placeholder = { Text("Введите вашу фамилию") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Имя") },
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Введите ваше имя") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Отчество") },
                    value = patronymiс,
                    onValueChange = { patronymiс = it },
                    placeholder = { Text("Введите ваше отчество") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Дата рождения") },
                    value = "${birthday[0]}.${birthday[1]}.${birthday[2]}",
                    onValueChange = { birthday = listOf(it.toInt()) },
                    placeholder = { Text("Введите вашу дату рождения") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Пол: "
                    )
                    Text("Мужской")
                    Switch(
                        checked = (gender == PersonData.Gender.Woman),
                        onCheckedChange = {
                            gender = if (it) {
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
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Checkbox (
                        checked = isAgree,
                        onCheckedChange = { isAgree = it }
                    )
                    Text("Согласен(на) на обработку персональных данных")
                }
                Button({}) {
                    Text("Зарегистрироваться")
                }
            }
            else -> {
                Text("Ошибка")
            }
        }
    }
}
package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun LogScreen() {
    var mailOrTelephone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    TextField(
        label = { Text("Электроная почта или номер телефона") },
        value = mailOrTelephone,
        onValueChange = { mailOrTelephone = it },
        placeholder = { Text("Введите вашу электронную почту или номер телефона") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
    )
    TextField(
        label = { Text("Пароль") },
        value = password,
        onValueChange = { password = it },
        placeholder = { Text("Введите ваш пароль") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
    )
    Button( {
        if ('@' in mailOrTelephone) {
            TODO()
        }
    } ) {
        Text("Войти")
    }
}
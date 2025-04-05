package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson

@Composable
fun LogScreen(navController : NavHostController) {
    /*
    var itMailOrTelephone by remember { mutableStateOf("mail") }
    var mail by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text("Вход", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Column(Modifier.selectableGroup())
            {
                Row(
                    modifier = Modifier.clickable{ itMailOrTelephone = "mail" }
                ) {
                    Text("Вход по почте", modifier = Modifier.padding(vertical = 12.dp))
                    RadioButton(
                        selected = itMailOrTelephone == "mail",
                        onClick = { itMailOrTelephone = "mail" }
                    )
                }
                Row(
                    modifier = Modifier.clickable{ itMailOrTelephone = "telephone" }
                ) {
                    Text("Вход по номеру телефона", modifier = Modifier.padding(vertical = 12.dp))
                    RadioButton(
                        selected = itMailOrTelephone == "telephone",
                        onClick = { itMailOrTelephone = "telephone" }
                    )
                }
            }
        }
        item {
            when (itMailOrTelephone) {
                "mail" -> {
                    TextField(
                        label = { Text("Электроная почта") },
                        value = mail,
                        onValueChange = { mail = it },
                        placeholder = { Text("Введите вашу электронную почту") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }
                "telephone" -> {
                    TextField(
                        label = { Text("Номер телефона") },
                        value = telephone,
                        onValueChange = { telephone = it },
                        placeholder = { Text("Введите ваш номер телефона") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                }
            }
        }
        item {
            TextField(
                label = { Text("Пароль") },
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Введите ваш пароль") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button( {
                when (itMailOrTelephone) {
                    "mail" -> {
                        for (i in 0..accounts.size - 1) {
                            if (accounts[i].mail == mail) {
                                if (accounts[i].password == password) {
                                    loggedInPerson[accounts.size - 1] = accounts[accounts.size - 1].password
                                    currentPerson = accounts[accounts.size - 1]
                                    navController.navigate(NavRoutes.Account.route)
                                }
                            }
                        }
                    }
                    "telephone" -> {
                        for (i in 0..accounts.size - 1) {
                            if (accounts[i].telephoneNumber == telephone) {
                                if (accounts[i].password == password) {
                                    loggedInPerson[accounts.size - 1] = accounts[accounts.size - 1].password
                                    currentPerson = accounts[accounts.size - 1]
                                    navController.navigate(NavRoutes.Account.route)
                                }
                            }
                        }
                    }
                }
            }) {
                Text("Войти")
            }
        }
    }

     */
}
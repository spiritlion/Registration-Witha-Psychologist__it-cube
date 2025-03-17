package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import android.telephony.PhoneNumberUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData.Gender
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.accounts
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import com.example.registrationwithapsychologist__itcube___newversion.loggedInPerson
import java.time.LocalDate
import java.time.Month
import kotlin.String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var state by remember { mutableIntStateOf(1) }

    var mail by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
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
                    label = { Text("Номер телефона") },
                    value = telephone,
                    onValueChange = { telephone = it },
                    placeholder = { Text("Введите ваш номер телефона") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
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
                    {
                        if (telephone.isNotEmpty()) {
                            if (telephone[0] == '+') currentPerson.telephoneNumber = telephone
                            else currentPerson.telephoneNumber = "+$telephone"
                        }
                        if (
                            mail != "" &&
                            password == currentPassword &&
                            password != "" &&
                            telephone.length == 12
                        ) state ++

                    }
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
                    placeholder = { Text("Введите вашу фамилию") }
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Имя") },
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Введите ваше имя") }
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text("Отчество") },
                    value = patronymiс,
                    onValueChange = { patronymiс = it },
                    placeholder = { Text("Введите ваше отчество") }
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
                    val options = listOf(PersonData.Gender.Man, PersonData.Gender.Woman)
                    var expanded by remember { mutableStateOf(false) }
                    // We want to react on tap/press on TextField to show menu
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                    ) {
                        TextField(
                            // The `menuAnchor` modifier must be passed to the text field for correctness.
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = when (gender) {
                                PersonData.Gender.Man -> "Мужской"
                                PersonData.Gender.Woman -> "Женский"
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
                                    text = { Text(when (selectionOption) {
                                        PersonData.Gender.Man -> "Мужской"
                                        PersonData.Gender.Woman -> "Женский"
                                    }) },
                                    onClick = {
                                        gender = selectionOption
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Checkbox (
                        checked = isAgree,
                        onCheckedChange = { isAgree = it }
                    )
                    Text("Согласен(на) на обработку персональных данных")
                }
                Button( {
                    if (
                        surname != "" &&
                        name != "" &&
                        patronymiс != "" &&
                        isAgree
                    ) {
                        accounts.add(
                            PersonData(
                                surname = surname,
                                name = name,
                                patronymiс = patronymiс,
                                mail = mail,
                                password = password,
                                birthday = LocalDate.of(birthday[2], birthday[1], birthday[0]),
                                gender = gender,
                                telephoneNumber = telephone
                            )
                        )
                        loggedInPerson[accounts.size - 1] = accounts[accounts.size - 1].password
                        currentPerson = accounts[accounts.size - 1]
                        navController.navigate(NavRoutes.Account.route)
                    }
                } ) {
                    Text("Зарегистрироваться")
                }
            }
            else -> {
                Text("Ошибка")
            }
        }
    }
}
package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.example.registrationwithapsychologist__itcube___newversion.registrationUserWithEmail
import com.example.registrationwithapsychologist__itcube___newversion.avatars
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun RegistrationScreen(modifier: Modifier = Modifier, navController: NavHostController, auth: FirebaseAuth, db : FirebaseFirestore, users: CollectionReference) {
    var state by remember { mutableIntStateOf(1) }

    var mail by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var patronymiс by remember { mutableStateOf("") }
    var birthday : Timestamp? by remember { mutableStateOf(null) }
    var genderIsMan by remember { mutableStateOf(true) }
    var isAgree by remember { mutableStateOf(false) }

    var avatar by remember { mutableIntStateOf(avatars[0]) }
    var changeAvatar by remember { mutableStateOf(false) }
    var isSuccessful by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            1 -> {
                item {
                    Text("Регистрация", fontSize = 20.sp)
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    TextField(
                        label = { Text("Электроная почта") },
                        value = mail,
                        onValueChange = { mail = it },
                        placeholder = { Text("Введите вашу электронную почту") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    TextField(
                        label = { Text("Номер телефона") },
                        value = telephone,
                        onValueChange = { telephone = it },
                        placeholder = { Text("Введите ваш номер телефона") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
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
                }
                item {
                    TextField(
                        label = { Text("Подтвердите пароль") },
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        placeholder = { Text("Введите ваш пароль ещё раз") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    TextField(
                        label = { Text("Фамилия") },
                        value = surname,
                        onValueChange = { surname = it },
                        placeholder = { Text("Введите вашу фамилию") }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    TextField(
                        label = { Text("Имя") },
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Введите ваше имя") }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    TextField(
                        label = { Text("Отчество") },
                        value = patronymiс,
                        onValueChange = { patronymiс = it },
                        placeholder = { Text("Введите ваше отчество") }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    var showDataPicker by remember { mutableStateOf(false) }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        birthday?.toDate().let {
                            if (it != null) {
                                Text("День рождения: ${it.date}.${it.month + 1}.${it.year + 1900}")
                            } else {
                                Text("День рождения: не выбран")
                            }
                        }
                        // Creating a button that on
                        // click displays/shows the DatePickerDialog
                        Button(
                            onClick = {
                                showDataPicker = true
                            }
                        ) {
                            Text(text = "Выбрать дату", color = Color.White)
                        }

                        if (showDataPicker) {
                            val datePickerState = rememberDatePickerState()
                            val confirmEnabled = remember {
                                derivedStateOf { datePickerState.selectedDateMillis != null }
                            }
                            DatePickerDialog(
                                onDismissRequest = {
                                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                                    // button. If you want to disable that functionality, simply use an empty
                                    // onDismissRequest.
                                    showDataPicker = false
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            birthday =
                                                Timestamp(java.util.Date(datePickerState.selectedDateMillis!!))
                                            showDataPicker = false
                                        },
                                        enabled = confirmEnabled.value
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        showDataPicker = false
                                    }) { Text("Cancel") }
                                }
                            ) {
                                // The verticalScroll will allow scrolling to show the entire month in case there is not
                                // enough horizontal space (for example, when in landscape mode).
                                // Note that it's still currently recommended to use a DisplayMode.Input at the state in
                                // those cases.
                                DatePicker(
                                    state = datePickerState,
                                    modifier = Modifier.verticalScroll(rememberScrollState())
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val options = listOf(true, false)
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
                                value = when (genderIsMan) {
                                    true -> "Мужской"
                                    false -> "Женский"
                                },
                                onValueChange = {  },
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
                                                    true -> "Мужской"
                                                    false -> "Женский"
                                                }
                                            )
                                        },
                                        onClick = {
                                            genderIsMan = selectionOption
                                            expanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Row {
                        Checkbox(
                            checked = isAgree,
                            onCheckedChange = { isAgree = it }
                        )
                        Text("Согласен(на) на обработку персональных данных")
                    }
                }
                item {
                    Button({
                        if (
                            mail != "" &&
                            telephone != "" &&
                            password != "" &&
                            currentPassword == password &&
                            surname != "" &&
                            name != "" &&
                            patronymiс != "" &&
                            isAgree
                        ) {
                            GlobalScope.launch {
                                isSuccessful = registrationUserWithEmail(
                                    email = mail,
                                    password = password,
                                    auth = auth,
                                    users = users,
                                    db = db,
                                    newPerson = PersonData(
                                        surname = surname,
                                        name = name,
                                        patronymiс = patronymiс,
                                        telephoneNumber = telephone,
                                        birthday = birthday,
                                        genderIsMan = genderIsMan
                                    )
                                )
                                state ++
                            }

                        }
                    }) {
                        Text("Далее")
                    }
                }
            }
            2 -> {
                var description = mutableStateOf(currentPerson!!.description)
                if (isSuccessful) {
                    item {
                        Text("Кастомизация", fontSize = 20.sp)
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Image(
                                painter = painterResource(avatar),
                                contentDescription = "avatar",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(64.dp)
                            )
                            Button(
                                { changeAvatar = true }
                            ) {
                                Text("Изменить аватар")
                            }
                        }
                    }
                    item {
                        TextField(
                            value = description.value.toString(),
                            onValueChange = {
                                description.value = it
                            },
                            placeholder = { Text("Что вы можете рассказать о себе") },
                            label = { Text(text = "О себе") },
                        )
                    }
                    item {
                        Button(
                            onClick = {
                                currentPerson!!.description = description.value
                                navController.navigate(NavRoutes.Account.route)
                            }
                        ) {
                            Text("Продолжить")
                        }
                    }
                }
            } else -> {
                item {
                    CircularProgressIndicator()
                }
            }
        }
    }
    if (changeAvatar) {
        AlertDialog(
            onDismissRequest = { changeAvatar = false },
            title = {
                Text("Выберите новый аватар")
            },
            text = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    for (el in avatars) {
                        item {
                            Image(
                                painter = painterResource(el),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .border(
                                        5.dp,
                                        if (el == currentPerson!!.image) {
                                            MaterialTheme.colorScheme.outline
                                        } else {
                                            Color.Transparent
                                        },
                                        CircleShape
                                    )
                                    .clickable {
                                        currentPerson!!.image = el
                                        changeAvatar = false
                                        avatar = currentPerson!!.image
                                    }
                            )
                        }
                    }
                }
            },
            confirmButton = {  },
            dismissButton = {
                Button( {changeAvatar = false} ) {
                    Text("Отмена")
                }
            }
        )
    }
}

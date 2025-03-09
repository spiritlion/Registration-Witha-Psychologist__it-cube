package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.R
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson

@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    var isEditingMode by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        if (!isEditingMode) {
            var isAddingBaby by remember { mutableStateOf(false) }
            var isShowBaby by remember { mutableStateOf(false) }
            var showBaby : PersonData.BabyData? by remember { mutableStateOf(null) }
            var isEditingBaby by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Профиль",
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontSize = 30.sp
                )
                Image(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(35.dp)
                        .clickable { isEditingMode = true }
                )
            }
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.account_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                        )
                        Text(currentPerson.surname)
                        Text(currentPerson.name)
                        Text(currentPerson.patronymiс)
                    }
                }
                item {
                    Text("Дата рождения: ${currentPerson.birthday}")
                }
                item {
                    Text("Email: ${currentPerson.mail}")
                }
                item {
                    Text(
                        "Пол: ${
                            when (currentPerson.gender) {
                                PersonData.Gender.Man -> "Мужской"
                                PersonData.Gender.Woman -> "Женский"
                            }
                        }"
                    )
                }
                item {
                    Text("О себе: ${currentPerson.description}")
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Дети:")
                        for (el in currentPerson.childrens) {
                            Row(modifier = modifier
                                .border(1.dp, Color.LightGray)
                                .clickable {
                                    isShowBaby = true
                                    showBaby = el
                                }
                            ) {
                                when (el.gender) {
                                    PersonData.Gender.Man -> Image(painter = painterResource(R.drawable.baby_boy_face), null)
                                    PersonData.Gender.Woman -> Image(painter = painterResource(R.drawable.baby_girl_face), null)
                                }
                                Column{
                                    Text(el.surname)
                                    Text(el.name)
                                    Text(el.patronymiс)
                                    Text(
                                        "Пол: ${
                                            when (el.gender) {
                                                PersonData.Gender.Man -> "Мужской"
                                                PersonData.Gender.Woman -> "Женский"
                                            }
                                        }"
                                    )
                                }
                            }
                        }
                        Row {
                            Image(
                                painter = painterResource(R.drawable.add),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                            Text("Добавить нового ребёнка")
                        }
                    }

                }
            }
            if (isShowBaby) {
                AlertDialog(
                    onDismissRequest = { isShowBaby = false},
                    title = { Text(text = "Просмотр информации о ребёнке") },
                    text = {
                        LazyColumn {
                            item {
                                Text("Имя: ${showBaby!!.name}")
                            }
                            item {
                                Text("Фамилия: ${showBaby!!.surname}")
                            }
                            item {
                                Text("Отчество: ${showBaby!!.patronymiс}")
                            }
                            item {
                                Text("Пол: ${
                                    when (showBaby!!.gender) {
                                        PersonData.Gender.Man -> "Мужской"
                                        PersonData.Gender.Woman -> "Женский"
                                    }
                                }")
                            }
                        }
                           },
                    confirmButton = {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Button({ isShowBaby = false }) {
                                Text("Ок", fontSize = 22.sp)
                            }
                            Button({ isEditingBaby = true }) {
                                Text("Изменить", fontSize = 22.sp)
                            }
                        }
                    }
                )
            }
        } else {
            var isSave by remember { mutableStateOf(false) }

            var intermediateSurname by remember { mutableStateOf(currentPerson.surname) }
            var intermediateName by remember { mutableStateOf(currentPerson.name) }
            var intermediatePatronymiс by remember { mutableStateOf(currentPerson.patronymiс) }
            var intermediateBirthday by remember { mutableStateOf(currentPerson.birthday) }
            var intermediateMail by remember { mutableStateOf(currentPerson.mail) }
            var intermediateGender by remember { mutableStateOf(currentPerson.gender) }
            var intermediateDescription by remember { mutableStateOf(currentPerson.description) }
            Box(modifier = Modifier.fillMaxWidth()) {
                Text("Завершить редактирование",
                    color = Color.Blue,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            if (
                                intermediateSurname != currentPerson.surname ||
                                intermediateName != currentPerson.name ||
                                intermediatePatronymiс != currentPerson.patronymiс ||
                                intermediateMail != currentPerson.mail ||
                                intermediateBirthday != currentPerson.birthday ||
                                intermediateGender != currentPerson.gender ||
                                intermediateDescription != currentPerson.description
                            ) {
                                isSave = true
                            } else {
                                isEditingMode = false
                            }
                        }
                )
            }
            LazyColumn {
                item {
                    Image(
                        painter = painterResource(R.drawable.account_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                    )
                }
                item {
                    TextField(
                        value = intermediateSurname,
                        onValueChange = { stringParameter ->
                            intermediateSurname = stringParameter
                        },
                        placeholder = { Text("Введите вашу фамилию") },
                        label = { Text(text = "Фамилия") }
                    )
                }
                item {
                    TextField(
                        value = intermediateName,
                        onValueChange = { stringParameter ->
                            intermediateName = stringParameter
                        },
                        placeholder = { Text("Введите ваше имя") },
                        label = { Text(text = "Имя") }
                    )
                }
                item {
                    TextField (
                        value = intermediatePatronymiс,
                        onValueChange = {
                                stringParameter ->
                            intermediatePatronymiс = stringParameter
                        },
                        placeholder = { Text("Введите вашу отчество (если есть)") },
                        label = { Text(text = "Отчество") }
                    )
                }
                item {
                    var showDatePicker by remember { mutableStateOf(false) }

                    if (showDatePicker) {
                        AlertDialog(
                            onDismissRequest = { showDatePicker = false },
                            title = { Text("Выберите дату своего дня рождения") },
                            text = {
                                Text("Здесь будет выбор даты рождения")
                            },
                            confirmButton = {
                                Button(
                                    {

                                    }
                                ) {
                                    Text("Подтвердить")
                                }
                            },
                            dismissButton = {
                                Button(
                                    {
                                        showDatePicker = false
                                    }
                                ) {
                                    Text("Отмена")
                                }
                            }
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Дата рождения: ${currentPerson.birthday}"
                        )
                        Button(onClick = { showDatePicker = true }) {
                            Text("Изменить")
                        }
                    }
                }
                item {
                    TextField (
                        value = intermediateMail,
                        onValueChange = {
                                stringParameter ->
                            intermediateMail = stringParameter
                        },
                        placeholder = { Text("Введите ваш Mail") },
                        label = { Text(text = "Mail") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                }
                item {
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
                item {
                    TextField (
                        value = intermediateDescription.toString(),
                        onValueChange = {
                                stringParameter ->
                            intermediateDescription = stringParameter
                        },
                        placeholder = { Text("Что вы можете рассказать о себе") },
                        label = { Text(text = "О себе") },

                        )
                }

            }

            if (isSave) {
                AlertDialog(
                    onDismissRequest = { isSave = false},
                    title = { Text(text = "Внимание") },
                    text = { Text("Вы не сохранили некотрые изменения") },
                    dismissButton = {
                        Button({ isSave = false }) {
                            Text("Назад", fontSize = 22.sp)
                        }
                    },
                    confirmButton = {
                        Row {
                            Button(
                                {
                                    currentPerson.surname = intermediateSurname
                                    currentPerson.name = intermediateName
                                    currentPerson.patronymiс = intermediatePatronymiс
                                    currentPerson.mail = intermediateMail
                                    currentPerson.birthday = intermediateBirthday
                                    currentPerson.description = intermediateDescription
                                    currentPerson.gender = intermediateGender

                                    isSave = false
                                    isEditingMode = false
                                }
                            ) {
                                Text("Сохранить")
                            }
                            Button(
                                {
                                    isSave = false
                                    isEditingMode = false
                                }
                            ) {
                                Text("Не сохранять")
                            }
                        }
                    }
                )
            }
        }
    }
}
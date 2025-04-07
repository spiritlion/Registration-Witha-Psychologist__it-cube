package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.example.registrationwithapsychologist__itcube___newversion.R
import com.example.registrationwithapsychologist__itcube___newversion.avatars
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(modifier: Modifier = Modifier, navController : NavHostController, auth: FirebaseAuth, db : FirebaseFirestore, users: CollectionReference) {
    var isEditingMode by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        if (currentPerson != null) {
            if (!isEditingMode) {
                var isAddingBaby by remember { mutableStateOf(false) }
                var isShowBaby by remember { mutableStateOf(false) }
                var showBaby: PersonData.BabyData? by remember { mutableStateOf(null) }
                var isEditingBaby by remember { mutableStateOf(false) }
                var isСhangeAccount by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Профиль",
                        modifier = Modifier
                            .align(Alignment.Center),
                        fontSize = 30.sp
                    )
                    Icon(
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
                                painter = painterResource(currentPerson!!.image!!),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                            )
                            Text(" ")
                            Text(currentPerson!!.surname!!)
                            Text(" ")
                            Text(currentPerson!!.name!!)
                            Text(" ")
                            Text(currentPerson!!.patronymiс!!)
                        }
                    }
                    item {
                        Text("Дата рождения: ${currentPerson!!.birthday!!}")
                    }
                    item {
                        Text("Email: ${auth.currentUser?.email}")
                    }
                    item {
                        //Text("Телефон: ${currentPerson.telephoneNumber.slice(0..1)}(${currentPerson.telephoneNumber.slice(2..4)})${currentPerson.telephoneNumber.slice(5..7)}-${currentPerson.telephoneNumber.slice(8..9)}-${currentPerson.telephoneNumber.slice(10..11)}")
                        Text("Телефон: ${currentPerson!!.telephoneNumber!!}")
                    }
//                item {
//                    Text(
//                        "Пол: ${
//                            when (currentPerson.gender) {
//                                PersonData.Gender.Man -> "Мужской"
//                                PersonData.Gender.Woman -> "Женский"
//                            }
//                        }"
//                    )
//                }
                    item {
                        Text("О себе: ${currentPerson?.description ?: "empty"}")
                    }

                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("Дети:")
                            for (el in currentPerson?.children ?: listOf<PersonData.BabyData>()) {
                                Row(modifier = modifier
                                    .border(1.dp, Color.LightGray)
                                    .clickable {
                                        isShowBaby = true
                                        showBaby = el
                                    }
                                ) {
                                    when (el.genderIsMan) {
                                        true -> Icon(
                                            painter = painterResource(R.drawable.baby_boy_face),
                                            null
                                        )

                                        false -> Icon(
                                            painter = painterResource(R.drawable.baby_girl_face),
                                            null
                                        )

                                        else -> {}
                                    }
                                    Column {
                                        Text(el.surname ?: "no surname")
                                        Text(el.name ?: "no name")
                                        Text(el.patronymiс ?: "no patrynomic")
                                        Text(
                                            "Пол: ${
                                                when (el.genderIsMan) {
                                                    true -> "Мужской"
                                                    false -> "Женский"
                                                    else -> {}
                                                }
                                            }"
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier.clickable { isAddingBaby = true }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.add),
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text("Добавить нового ребёнка")
                            }
                        }
                    }

                    item {
                        Button(
                            onClick = { isСhangeAccount = true },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Сменить аккаунт")
                        }
                    }
                }
                if (isShowBaby) {
                    AlertDialog(
                        onDismissRequest = { isShowBaby = false },
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
                                    Text(
                                        "Пол: ${
                                            when (showBaby!!.genderIsMan) {
                                                true -> "Мужской"
                                                false -> "Женский"
                                                else -> {}
                                            }
                                        }"
                                    )
                                }
                            }
                        },
                        confirmButton = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
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
                if (isEditingBaby) {
                    var intermediateSurname by remember { mutableStateOf(showBaby!!.surname) }
                    var intermediateName by remember { mutableStateOf(showBaby!!.name) }
                    var intermediatePatronymiс by remember { mutableStateOf(showBaby!!.patronymiс) }
                    var intermediateGender by remember { mutableStateOf(showBaby!!.genderIsMan) }
                    AlertDialog(
                        onDismissRequest = { isShowBaby = false },
                        title = { Text(text = "Изменение информации о ребёнке") },
                        text = {
                            LazyColumn {
                                item {
                                    TextField(
                                        value = intermediateName ?: "",
                                        onValueChange = { stringParameter ->
                                            intermediateName = stringParameter
                                        },
                                        placeholder = { Text("Введите ваше имя") },
                                        label = { Text(text = "Имя") }
                                    )
                                }
                                item {
                                    TextField(
                                        value = intermediateSurname ?: "",
                                        onValueChange = { stringParameter ->
                                            intermediateSurname = stringParameter
                                        },
                                        placeholder = { Text("Введите вашу фамилию") },
                                        label = { Text(text = "Фамилия") }
                                    )
                                }
                                item {
                                    TextField(
                                        value = intermediatePatronymiс ?: "",
                                        onValueChange = { stringParameter ->
                                            intermediatePatronymiс = stringParameter
                                        },
                                        placeholder = { Text("Введите ваше отчество") },
                                        label = { Text(text = "Отчество") }
                                    )
                                }
                                item {
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
                                            value = when (intermediateGender) {
                                                true -> "Мужской"
                                                false -> "Женский"
                                                else -> ""
                                            },
                                            onValueChange = {},
                                            label = { Text("Пол") },
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = expanded
                                                )
                                            },
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
                                                        intermediateGender = selectionOption
                                                        expanded = false
                                                    },
                                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button({ isEditingBaby = false }) {
                                        Text("Отмена", fontSize = 22.sp)
                                    }
                                    Button({
                                        showBaby!!.genderIsMan = intermediateGender
                                        showBaby!!.name = intermediateName
                                        showBaby!!.surname = intermediateSurname
                                        showBaby!!.patronymiс = intermediatePatronymiс
                                        users.document(auth.uid!!).set(currentPerson!!)
                                        isShowBaby = false
                                        isEditingBaby = false
                                        navController.navigate(NavRoutes.Account.route)
                                    }) {
                                        Text("Сохранить", fontSize = 18.sp)
                                    }
                                }
                                Button(
                                    onClick = {
                                        isEditingBaby = false
                                        isShowBaby = false
                                        currentPerson!!.children!!.remove(showBaby)
                                        users.document(auth.uid!!).set(currentPerson!!)
                                        navController.navigate(NavRoutes.Account.route)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red
                                    )
                                ) {
                                    Text("Удалить информацию о ребёнке")
                                }
                            }

                        }
                    )
                }
                if (isAddingBaby) {
                    var intermediateBabyName by remember { mutableStateOf("") }
                    var intermediateBabySurname by remember { mutableStateOf("") }
                    var intermediateBabyPatronymic by remember { mutableStateOf("") }
                    var intermediateBabyGenderIsMan by remember { mutableStateOf(true) }
                    AlertDialog(
                        onDismissRequest = { isAddingBaby = false },
                        title = { Text("Добавление информации о ребёнке") },
                        text = {
                            LazyColumn {
                                item {
                                    TextField(
                                        value = intermediateBabyName,
                                        onValueChange = { stringParameter ->
                                            intermediateBabyName = stringParameter
                                        },
                                        placeholder = { Text("Введите ваше имя") },
                                        label = { Text(text = "Имя") }
                                    )
                                }
                                item {
                                    TextField(
                                        value = intermediateBabySurname,
                                        onValueChange = { stringParameter ->
                                            intermediateBabySurname = stringParameter
                                        },
                                        placeholder = { Text("Введите вашу фамилию") },
                                        label = { Text(text = "Фамилия") }
                                    )
                                }
                                item {
                                    TextField(
                                        value = intermediateBabyPatronymic,
                                        onValueChange = { stringParameter ->
                                            intermediateBabyPatronymic = stringParameter
                                        },
                                        placeholder = { Text("Введите ваше отчество") },
                                        label = { Text(text = "Отчество") }
                                    )
                                }
                                item {
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
                                            value = when (intermediateBabyGenderIsMan) {
                                                true -> "Мужской"
                                                false -> "Женский"
                                            },
                                            onValueChange = {},
                                            label = { Text("Пол") },
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = expanded
                                                )
                                            },
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
                                                        intermediateBabyGenderIsMan =
                                                            selectionOption
                                                        expanded = false
                                                    },
                                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Row {
                                Button({ isAddingBaby = false }) {
                                    Text("Отмена")
                                }
                                Button({
                                    currentPerson!!.children!!.add(
                                        PersonData.BabyData(
                                            surname = intermediateBabySurname,
                                            name = intermediateBabyName,
                                            patronymiс = intermediateBabyPatronymic,
                                            genderIsMan = intermediateBabyGenderIsMan
                                        )
                                    )
                                    users.document(auth.uid!!).set(currentPerson!!)
                                    isAddingBaby = false
                                    navController.navigate(NavRoutes.Account.route)
                                }) {
                                    Text("Сохранить")
                                }
                            }
                        }
                    )
                }
                /*
                if (isСhangeAccount) {
                    AlertDialog(
                        onDismissRequest = { isСhangeAccount = false },
                        title = { Text("Смена аккаунта") },
                        text = {
                            LazyColumn {
                                loggedInPerson.forEach { person ->
                                    item {
                                        Row(modifier = Modifier
                                            .border(1.dp, Color.Gray)
                                            .clickable{
                                                currentPerson = accounts[person.key]
                                                navController.navigate(NavRoutes.Account.route)
                                            }
                                            .fillMaxWidth()
                                        ) {
                                            Image(
                                                painter = painterResource(currentPerson.image),
                                                contentDescription = null,
                                                modifier = Modifier.size(70.dp)
                                            )
                                            Column {
                                                Text(accounts[person.key].surname)
                                                Text(accounts[person.key].name)
                                                Text(accounts[person.key].patronymiс)
                                                Text(accounts[person.key].mail)
                                                Text( "${accounts[person.key].telephoneNumber.slice(0..1)}(${accounts[person.key].telephoneNumber.slice(2..4)})${accounts[person.key].telephoneNumber.slice(5..7)}-${accounts[person.key].telephoneNumber.slice(7..8)}-${currentPerson.telephoneNumber.slice(9..10)}")

                                            }
                                        }
                                    }
                                }
                                item {
                                    Button( { navController.navigate(NavRoutes.Registration.route) } ) {
                                        Text("Зарегистрироваться")
                                    }
                                }
                                item {
                                    Button( { navController.navigate(NavRoutes.Log.route) } ) {
                                        Text("Войти")
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                { isСhangeAccount = false }
                            ) {
                                Text("Отмена")
                            }
                        }
                    )
                }
                */
            } else {
                var isSave by remember { mutableStateOf(false) }

                var intermediateSurname by remember { mutableStateOf(currentPerson!!.surname) }
                var intermediateName by remember { mutableStateOf(currentPerson!!.name) }
                var intermediatePatronymiс by remember { mutableStateOf(currentPerson!!.patronymiс) }
                var intermediateBirthday by remember { mutableStateOf(currentPerson!!.birthday) }
                var intermediateMail by remember { mutableStateOf(auth.currentUser!!.email) }
                var intermediateTelephone by remember { mutableStateOf(currentPerson!!.telephoneNumber) }
                var intermediateGender by remember { mutableStateOf(currentPerson!!.genderIsMan) }
                var intermediateDescription by remember { mutableStateOf(currentPerson!!.description) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text("Завершить редактирование",
                        color = Color.Blue,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable {
                                if (
                                    intermediateSurname != currentPerson!!.surname ||
                                    intermediateName != currentPerson!!.name ||
                                    intermediatePatronymiс != currentPerson!!.patronymiс ||
                                    intermediateMail != auth.currentUser!!.email ||
                                    intermediateTelephone != currentPerson!!.telephoneNumber ||
                                    intermediateBirthday != currentPerson!!.birthday ||
                                    intermediateGender != currentPerson!!.genderIsMan ||
                                    intermediateDescription != currentPerson!!.description
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(currentPerson!!.image!!),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                            )
                            var changeAvatar by remember { mutableStateOf(false) }
                            Button(
                                { changeAvatar = true }
                            ) {
                                Text("Сменить аватар")
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
                                                            }
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    confirmButton = { },
                                    dismissButton = {
                                        Button({ changeAvatar = false }) {
                                            Text("Отмена")
                                        }
                                    }
                                )
                            }
                        }
                    } // аватар
                    item {
                        TextField(
                            value = intermediateSurname ?: "",
                            onValueChange = { stringParameter ->
                                intermediateSurname = stringParameter
                            },
                            placeholder = { Text("Введите вашу фамилию") },
                            label = { Text(text = "Фамилия") }
                        )
                    } // смена фамилии
                    item {
                        TextField(
                            value = intermediateName ?: "",
                            onValueChange = { stringParameter ->
                                intermediateName = stringParameter
                            },
                            placeholder = { Text("Введите ваше имя") },
                            label = { Text(text = "Имя") }
                        )
                    } // смена имени
                    item {
                        TextField(
                            value = intermediatePatronymiс ?: "",
                            onValueChange = { stringParameter ->
                                intermediatePatronymiс = stringParameter
                            },
                            placeholder = { Text("Введите вашу отчество (если есть)") },
                            label = { Text(text = "Отчество") }
                        )
                    } // смена отчества
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
                                "Дата рождения: ${currentPerson!!.birthday}"
                            )
                            Button(onClick = { showDatePicker = true }) {
                                Text("Изменить")
                            }
                        }
                    } // смена даты рождения
                    item {
                        TextField(
                            value = intermediateMail ?: "",
                            onValueChange = { stringParameter ->
                                intermediateMail = stringParameter
                            },
                            placeholder = { Text("Введите ваш Mail") },
                            label = { Text(text = "Mail") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    } // смена mail
                    item {
                        TextField(
                            value = intermediateTelephone ?: "",
                            onValueChange = { stringParameter ->
                                intermediateTelephone = stringParameter
                            },
                            placeholder = { Text("Введите ваш телефон") },
                            label = { Text(text = "Телефон") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )
                    } // смена телефона
                    item {
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
                                value = when (intermediateGender) {
                                    true -> "Мужской"
                                    false -> "Женский"
                                    else -> ""
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
                                                    true -> "Мужской"
                                                    false -> "Женский"
                                                }
                                            )
                                        },
                                        onClick = {
                                            intermediateGender = selectionOption
                                            expanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    )
                                }
                            }
                        }
                    } // смена "пола"
                    item {
                        TextField(
                            value = intermediateDescription.toString(),
                            onValueChange = { stringParameter ->
                                intermediateDescription = stringParameter
                            },
                            placeholder = { Text("Что вы можете рассказать о себе") },
                            label = { Text(text = "О себе") },

                            )
                    } // изменение информации "о себе"
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(20.dp))
                            Button(
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF9800)
                                )
                            ) {
                                Text("Выйти из аккаунта")
                            }
                            Button(
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                )
                            ) {
                                Text("Удалить аккаунт")
                            }
                        }
                    } // выйти из аккаунта / удалить аккаунт
                }
                if (isSave) {
                    AlertDialog(
                        onDismissRequest = { isSave = false },
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
                                        currentPerson!!.surname = intermediateSurname
                                        currentPerson!!.name = intermediateName
                                        currentPerson!!.patronymiс = intermediatePatronymiс

                                        if (intermediateTelephone!![0] == '+') currentPerson!!.telephoneNumber =
                                            intermediateTelephone
                                        else currentPerson!!.telephoneNumber =
                                            "+$intermediateTelephone"
                                        currentPerson!!.birthday = intermediateBirthday
                                        currentPerson!!.description = intermediateDescription
                                        currentPerson!!.genderIsMan = intermediateGender

                                        auth.currentUser!!.updateEmail(intermediateMail!!)
                                        users.document(auth.uid!!).set(currentPerson!!)
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
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                Text("Что-бы просматривать информацию об аккаунте, необходимо войти или зарегистроваться:", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    {
                        navController.navigate(NavRoutes.Log.route)
                    }
                ) {
                    Text("Войти")
                }
                Button(
                    {
                        navController.navigate(NavRoutes.Registration.route)
                    }
                ) {
                    Text("Зарегистрироваться")
                }
            }
        }
    }
}


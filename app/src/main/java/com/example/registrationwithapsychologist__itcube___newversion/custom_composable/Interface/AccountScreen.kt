package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.example.registrationwithapsychologist__itcube___newversion.R
import com.example.registrationwithapsychologist__itcube___newversion.avatars
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts.Record
import com.example.registrationwithapsychologist__itcube___newversion.deleteCurrentAccount
import com.example.registrationwithapsychologist__itcube___newversion.listPsychologs
import com.example.registrationwithapsychologist__itcube___newversion.listRecordsWithId
import com.example.registrationwithapsychologist__itcube___newversion.listUsersWithId
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun AccountScreen(modifier: Modifier = Modifier, navController : NavHostController, auth: FirebaseAuth, db : FirebaseFirestore, users: CollectionReference, records: CollectionReference) {
    var isEditingMode by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        currentPerson?.let { person ->
            val snackbarHostState = remember { SnackbarHostState() }

            var itIsPsychologist = false
            listPsychologs.forEach {
                if (auth.uid == it.id) {
                    itIsPsychologist = true
                }
            }
            if (!isEditingMode) {
                if (!itIsPsychologist) {
                    // region аккаунт пользователя
                    var isAddingBaby by remember { mutableStateOf(false) }
                    var isShowBaby by remember { mutableStateOf(false) }
                    var showBaby: PersonData.BabyData? by remember { mutableStateOf(null) }
                    var isEditingBaby by remember { mutableStateOf(false) }
                    var isShowRecord by remember { mutableStateOf(false) }
                    var showRecord: Pair<String, Record>? by remember { mutableStateOf(null) }
                    var isDeleteRecord: Boolean by remember { mutableStateOf(false) }
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
                                    painter = painterResource(person.image),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                )
                                Text(" ")
                                Text(person.surname!!)
                                Text(" ")
                                Text(person.name!!)
                                Text(" ")
                                Text(person.patronymiс!!)
                            }
                        }
                        item {
                            person.birthday!!.toDate().let { Text("Дата рождения: ${it.date}.${it.month + 1}.${it.year + 1900}")}
                        }
                        item {
                            Text("Email: ${auth.currentUser?.email}")
                        }
                        item {
                            //Text("Телефон: ${person.telephoneNumber.slice(0..1)}(${person.telephoneNumber.slice(2..4)})${person.telephoneNumber.slice(5..7)}-${person.telephoneNumber.slice(8..9)}-${person.telephoneNumber.slice(10..11)}")
                            Text("Телефон: ${person.telephoneNumber!!}")
                        }
                        item {
                            Text(
                                "Пол: ${
                                    when (person.genderIsMan) {
                                        true -> "Мужской"
                                        false -> "Женский"
                                        else -> "error"
                                    }
                                }"
                            )
                        }
                        item {
                            Text("О себе: ${person.description ?: "empty"}")
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text("Дети:")
                                for (el in person.children
                                    ?: listOf<PersonData.BabyData>()) {
                                    Box(
                                        modifier = modifier
                                            .clickable {
                                                isShowBaby = true
                                                showBaby = el
                                            }
                                            .size(180.dp, 100.dp)
                                            .background(
                                                brush = Brush.linearGradient(
                                                    colors = when (el.genderIsMan) {
                                                        false -> listOf(
                                                            Color(0xFFEF629A),
                                                            Color(0xFFEECDA1)
                                                        )

                                                        true -> listOf(
                                                            Color(0xFF2C67F2),
                                                            Color(0xFF62CFF4)
                                                        )

                                                        else -> {
                                                            listOf(Color.Gray, Color.DarkGray)
                                                        }
                                                    }
                                                ),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clip(
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
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
                                            when (el.genderIsMan) {
                                                true -> Icon(
                                                    painter = painterResource(R.drawable.baby_boy_face),
                                                    null,
                                                    tint = Color(0x66FFFFFF)
                                                )

                                                false -> Icon(
                                                    painter = painterResource(R.drawable.baby_girl_face),
                                                    null,
                                                    tint = Color(0x66FFFFFF)
                                                )

                                                else -> {}
                                            }
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
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text("Ваши сеансы:")
                                for (el in listRecordsWithId) {
                                    if (el.second.user == auth.uid) {
                                        Box(
                                            modifier = Modifier
                                                .size(180.dp, 100.dp)
                                                .background(
                                                    brush = Brush.linearGradient(
                                                        colors = when (el.second.state) {
                                                            Record.State.NotYet -> listOf(
                                                                Color.Gray,
                                                                Color.LightGray
                                                            )

                                                            Record.State.Сonducted -> listOf(
                                                                Color(0xFF49C628),
                                                                Color(0xFF70F570)
                                                            )

                                                            Record.State.Cancelled -> listOf(
                                                                Color(0xFFFF3300),
                                                                Color(0xFFFF8800)
                                                            )
                                                        }
                                                    ),
                                                    shape = RoundedCornerShape(10.dp)
                                                )
                                                .clip(
                                                    shape = RoundedCornerShape(10.dp)
                                                )
                                                .padding(if (el.second.state == Record.State.NotYet) 0.dp else 5.dp)
                                                .clickable {
                                                    isShowRecord = true
                                                    showRecord = el
                                                }
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                            ) {
                                                Text("${el.second.time.toDate().date}.${el.second.time.toDate().month + 1}.${el.second.time.toDate().year + 1900}")
                                                Text("${el.second.time.toDate().hours}:${el.second.time.toDate().minutes}${if (el.second.time.toDate().minutes == 0) "0" else ""}")
                                                Text(
                                                    text = when (el.second.state) {
                                                        Record.State.NotYet -> "Ещё не состоялся"
                                                        Record.State.Сonducted -> "Проведён"
                                                        Record.State.Cancelled -> "Отменён"
                                                    },
                                                    fontSize = 30.sp,
                                                    color = Color(0x66FFFFFF),
                                                )
                                            }
                                        }
                                    }
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
                                            users.document(auth.uid!!)
                                                .set(person)
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
                                            listRecordsWithId.forEach{ record ->
                                                if (record.second.user == auth.currentUser!!.uid) {
                                                    record.second.whoFromBabyIsRecording.forEach{ i ->
                                                        if (i == person.children!!.indexOf(showBaby!!)) {
                                                            record.second.whoFromBabyIsRecording.remove(i)
                                                        }
                                                    }
                                                }
                                            }
                                            person.children!!.remove(
                                                showBaby
                                            )
                                            users.document(auth.uid!!)
                                                .set(person)
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
                                        person!!.children!!.add(
                                            PersonData.BabyData(
                                                surname = intermediateBabySurname,
                                                name = intermediateBabyName,
                                                patronymiс = intermediateBabyPatronymic,
                                                genderIsMan = intermediateBabyGenderIsMan
                                            )
                                        )
                                        users.document(auth.uid!!)
                                            .set(person!!)
                                        isAddingBaby = false
                                        navController.navigate(NavRoutes.Account.route)
                                    }) {
                                        Text("Сохранить")
                                    }
                                }
                            }
                        )
                    }
                    if (isShowRecord) {
                        AlertDialog(
                            title = { Text("Просмотр информации о сеансе") },
                            text = {
                                Column {
                                    Text(showRecord!!.first)
                                    Text("Дата: ${showRecord!!.second.time.toDate().date}.${showRecord!!.second.time.toDate().month + 1}.${showRecord!!.second.time.toDate().year + 1900}")
                                    Text("Время: ${showRecord!!.second.time.toDate().hours}:${showRecord!!.second.time.toDate().minutes}${if (showRecord!!.second.time.toDate().minutes == 0) "0" else ""}")
                                    Text(
                                        "Статус: ${
                                            when (showRecord!!.second.state) {
                                                Record.State.NotYet -> "Ещё не состоялся"
                                                Record.State.Сonducted -> "Проведён"
                                                Record.State.Cancelled -> "Отменён"
                                            }
                                        }"
                                    )
                                    Text("Причина: ${showRecord!!.second.reason}")
                                    Text("Кто записался:")
                                    if (showRecord!!.second.isUserRecording) Text("• ${person?.surname} ${person?.name} ${person?.patronymiс} (Вы)")
                                    for (el in showRecord!!.second.whoFromBabyIsRecording) {
                                        person.children?.get(el)?.let { Text("• ${it.surname} ${it.name} ${it.patronymiс}") }
                                    }
                                    if (showRecord!!.second.state == Record.State.Cancelled) {
                                        Text(
                                            "Кто отменил: ${
                                                when (showRecord!!.second.whoCanceled) {
                                                    1 -> "пользователь"
                                                    2 -> "психолог"
                                                    else -> "error"
                                                }
                                            }"
                                        )
                                        Text("Причина отмены: ${showRecord!!.second.reasonForRefusal}")
                                    }
                                }
                            },
                            onDismissRequest = {
                                isShowRecord = false
                                showRecord = null
                            },
                            dismissButton = {
                                Button(
                                    { isDeleteRecord = true },
                                    enabled = showRecord!!.second.state != Record.State.Cancelled
                                ) { Text("Отменить") }
                            },
                            confirmButton = { Button({ isShowRecord = false }) { Text("Ок") } }
                        )
                    }
                    if (isDeleteRecord) {
                        var reason by remember { mutableStateOf("") }
                        AlertDialog(
                            onDismissRequest = { isDeleteRecord = false },
                            title = { Text("Вы уверенны, что хотите отменить этот сеанс и если да, то укажите пожалуйста причину:") },
                            text = {
                                TextField(
                                    value = reason,
                                    onValueChange = { reason = it },
                                    label = { Text("Причина") }
                                )
                            },
                            dismissButton = { Button({ isDeleteRecord = false }) { Text("Нет") } },
                            confirmButton = {
                                Button({
                                    GlobalScope.launch {
                                        isShowRecord = false
                                        isDeleteRecord = false
                                        showRecord!!.second.state = Record.State.Cancelled
                                        showRecord!!.second.whoCanceled = 1
                                        showRecord!!.second.reasonForRefusal = reason
                                        records.document(showRecord!!.first)
                                            .set(showRecord!!.second)
                                    }
                                    navController.navigate(NavRoutes.Account.route)
                                }
                                ) { Text("Да") }
                            }
                        )
                    }
                    if (isСhangeAccount) {
                        AlertDialog(
                            title = { Text("Смена аккаунта") },
                            text = {
                                Column {
                                    Button(
                                        onClick = { navController.navigate(NavRoutes.Log.route) }
                                    ) {
                                        Text("Войти в аккаунт")
                                    }
                                    Button(
                                        onClick = { navController.navigate(NavRoutes.Registration.route) }
                                    ) {
                                        Text("Создать новый аккаунт")
                                    }
                                }
                            },
                            onDismissRequest = { isСhangeAccount = false },
                            confirmButton = {
                                Button(
                                    onClick = { isСhangeAccount = false }
                                ) {
                                    Text("Отмена")
                                }
                            }
                        )
                    }
                    // endregion
                } else {
                    // region аккаунт психолога
                    var mode by remember { mutableIntStateOf(1) }
                    var isShowRecord by remember { mutableStateOf(false) }
                    var showRecord: Pair<String, Record>? by remember { mutableStateOf(null) }
                    var isShowUser by remember { mutableStateOf(false) }
                    var showUser: Pair<String, PersonData>? by remember { mutableStateOf(null) }
                    var isShowBaby by remember { mutableStateOf(false) }
                    var showBaby: PersonData.BabyData? by remember { mutableStateOf(null) }
                    var isDeleteRecord: Boolean by remember { mutableStateOf(false) }
                    Text("Добро пожаловать, ${person?.surname} ${person?.name} ${person?.patronymiс}")
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Записи",
                            modifier = Modifier.clickable { mode = 1 },
                            textDecoration = if (mode == 1) {
                                TextDecoration.Underline
                            } else {
                                TextDecoration.None
                            }
                        )
                        Text(
                            text = "Пользователи",
                            modifier = Modifier.clickable { mode = 2 },
                            textDecoration = if (mode == 2) {
                                TextDecoration.Underline
                            } else {
                                TextDecoration.None
                            }
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (mode) {
                            1 -> {
                                var search by remember { mutableStateOf("") }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextField(
                                        value = search,
                                        onValueChange = { search = it },
                                        label = { Text("Поиск") }
                                    )
                                    Icon(
                                        Icons.Filled.Search,
                                        "Search",
                                        modifier = Modifier
                                            .clickable {

                                            }
                                    )
                                }
                                listRecordsWithId.forEach { it ->
                                    Box(
                                        modifier = Modifier
                                            .size(180.dp, 100.dp)
                                            .background(
                                                brush = Brush.linearGradient(
                                                    colors = when (it.second.state) {
                                                        Record.State.NotYet -> listOf(
                                                            Color.Gray,
                                                            Color.LightGray
                                                        )

                                                        Record.State.Сonducted -> listOf(
                                                            Color(0xFF49C628),
                                                            Color(0xFF70F570)
                                                        )

                                                        Record.State.Cancelled -> listOf(
                                                            Color(0xFFFF3300),
                                                            Color(0xFFFF8800)
                                                        )
                                                    }
                                                ),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clip(
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .padding(if (it.second.state == Record.State.NotYet) 0.dp else 5.dp)
                                            .clickable {
                                                isShowRecord = true
                                                showRecord = it
                                            }
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                        ) {
                                            Text("${it.second.time.toDate().date}.${it.second.time.toDate().month + 1}.${it.second.time.toDate().year + 1900}")
                                            Text("${it.second.time.toDate().hours}:${it.second.time.toDate().minutes}${if (it.second.time.toDate().minutes == 0) "0" else ""}")
                                            Text(
                                                text = when (it.second.state) {
                                                    Record.State.NotYet -> "Ещё не состоялся"
                                                    Record.State.Сonducted -> "Проведён"
                                                    Record.State.Cancelled -> "Отменён"
                                                },
                                                fontSize = 30.sp,
                                                color = Color(0x66FFFFFF),
                                            )
                                        }
                                    }
                                }
                            }
                            2 -> {
                                var search by remember { mutableStateOf("") }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextField(
                                        value = search,
                                        onValueChange = { search = it },
                                        label = { Text("Поиск") }
                                    )
                                    Icon(
                                        Icons.Filled.Search,
                                        "Search",
                                        modifier = Modifier
                                            .clickable {

                                            }
                                    )
                                }
                                listUsersWithId.forEach { it ->
                                    Box(
                                        modifier = modifier
                                            .clickable {
                                                isShowUser = true
                                                showUser = it
                                            }
                                            .size(180.dp, 100.dp)
                                            .background(
                                                brush = Brush.linearGradient(
                                                    colors = when (it.second.genderIsMan) {
                                                        false -> listOf(
                                                            Color(0xFFEF629A),
                                                            Color(0xFFEECDA1)
                                                        )

                                                        true -> listOf(
                                                            Color(0xFF2C67F2),
                                                            Color(0xFF62CFF4)
                                                        )

                                                        else -> {
                                                            listOf(Color.Gray, Color.DarkGray)
                                                        }
                                                    }
                                                ),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clip(
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column {
                                                Text(it.second.surname ?: "no surname")
                                                Text(it.second.name ?: "no name")
                                                Text(it.second.patronymiс ?: "no patrynomic")
                                                Text(
                                                    "Пол: ${
                                                        when (it.second.genderIsMan) {
                                                            true -> "Мужской"
                                                            false -> "Женский"
                                                            else -> {}
                                                        }
                                                    }"
                                                )
                                            }
//                                            when (it.genderIsMan) {
//                                                true -> Icon(
//                                                    painter = painterResource(R.drawable.baby_boy_face),
//                                                    null,
//                                                    tint = Color(0x66FFFFFF)
//                                                )
//
//                                                false -> Icon(
//                                                    painter = painterResource(R.drawable.baby_girl_face),
//                                                    null,
//                                                    tint = Color(0x66FFFFFF)
//                                                )
//
//                                                else -> {}
//                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (isShowRecord) {
                        AlertDialog(
                            title = { Text("Просмотр информации о сеансе") },
                            text = {
                                Column {
                                    Text(showRecord!!.first)
                                    Text("Дата: ${showRecord!!.second.time.toDate().date}.${showRecord!!.second.time.toDate().month + 1}.${showRecord!!.second.time.toDate().year + 1900}")
                                    Text("Время: ${showRecord!!.second.time.toDate().hours}:${showRecord!!.second.time.toDate().minutes}${if (showRecord!!.second.time.toDate().minutes == 0) "0" else ""}")
                                    Text(
                                        "Статус: ${
                                            when (showRecord!!.second.state) {
                                                Record.State.NotYet -> "Ещё не состоялся"
                                                Record.State.Сonducted -> "Проведён"
                                                Record.State.Cancelled -> "Отменён"
                                            }
                                        }"
                                    )
                                    Text("Причина: ${showRecord!!.second.reason}")
                                    Text("Кто записался:")
                                    if (showRecord!!.second.isUserRecording) Text("• ${person.surname} ${person?.name} ${person?.patronymiс} (Пользователь)")
                                    for (el in showRecord!!.second.whoFromBabyIsRecording) {
                                        person.children!![el].let {  Text("• ${it.surname} ${it.name} ${it.patronymiс}") }
                                    }
                                    if (showRecord!!.second.state == Record.State.Cancelled) Text("Причина отмены: ${showRecord!!.second.reasonForRefusal}")
                                }
                            },
                            onDismissRequest = {
                                isShowRecord = false
                                showRecord = null
                            },
                            dismissButton = {
                                Button(
                                    { isDeleteRecord = true },
                                    enabled = showRecord!!.second.state != Record.State.Cancelled
                                ) { Text("Отменить") }
                            },
                            confirmButton = { Button({ isShowRecord = false }) { Text("Ок") } }
                        )
                    }
                    if (isDeleteRecord) {
                        var reason by remember { mutableStateOf("") }
                        AlertDialog(
                            onDismissRequest = { isDeleteRecord = false },
                            title = { Text("Вы уверенны, что хотите отменить этот сеанс и если да, то укажите пожалуйста причину:") },
                            text = {
                                TextField(
                                    value = reason,
                                    onValueChange = { reason = it },
                                    label = { Text("Причина") }
                                )
                            },
                            dismissButton = { Button({ isDeleteRecord = false }) { Text("Нет") } },
                            confirmButton = {
                                Button({
                                    GlobalScope.launch {
//                                isEditingBaby = false
//                                isShowBaby = false
//                                person!!.children!!.remove(showBaby)
//                                users.document(auth.uid!!).set(person!!)
//                                navController.navigate(NavRoutes.Account.route)
                                        isShowRecord = false
                                        isDeleteRecord = false
                                        showRecord!!.second.state = Record.State.Cancelled
                                        showRecord!!.second.whoCanceled = 2
                                        showRecord!!.second.reasonForRefusal = reason
                                        records.document(showRecord!!.first)
                                            .set(showRecord!!.second)
                                    }
                                    navController.navigate(NavRoutes.Account.route)
                                }
                                ) { Text("Да") }
                            }
                        )
                    }
                    if (isShowUser) {
                        AlertDialog(
                            title = { Text("Простмотр информации о человеке") },
                            text = {
                                LazyColumn {
                                    item {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(showUser!!.second.image),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape)
                                            )
                                            Text(" ")
                                            Text(showUser!!.second.surname!!)
                                            Text(" ")
                                            Text(showUser!!.second.name!!)
                                            Text(" ")
                                            Text(showUser!!.second.patronymiс!!)
                                        }
                                    }
                                    item {
                                        Text("Дата рождения: ${showUser!!.second.birthday!!.toDate()}")
                                    }
                                    item {
                                        Text("Email: ${auth.currentUser?.email}")
                                    }
                                    item {
                                        //Text("Телефон: ${showUser.telephoneNumber.slice(0..1)}(${showUser.telephoneNumber.slice(2..4)})${showUser.telephoneNumber.slice(5..7)}-${showUser.telephoneNumber.slice(8..9)}-${showUser.telephoneNumber.slice(10..11)}")
                                        Text("Телефон: ${showUser!!.second.telephoneNumber!!}")
                                    }
                                    item {
                                        Text(
                                            "Пол: ${
                                                when (showUser!!.second.genderIsMan) {
                                                    true -> "Мужской"
                                                    false -> "Женский"
                                                    else -> "error"
                                                }
                                            }"
                                        )
                                    }
                                    item {
                                        Text("О себе: ${showUser?.second?.description ?: "empty"}")
                                    }
                                    item {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.spacedBy(5.dp)
                                        ) {
                                            Text("Дети пользователя:")
                                            for (el in showUser?.second?.children
                                                ?: listOf<PersonData.BabyData>()) {
                                                Box(
                                                    modifier = modifier
                                                        .clickable {
                                                            isShowBaby = true
                                                            showBaby = el
                                                        }
                                                        .size(180.dp, 100.dp)
                                                        .background(
                                                            brush = Brush.linearGradient(
                                                                colors = when (el.genderIsMan) {
                                                                    false -> listOf(
                                                                        Color(0xFFEF629A),
                                                                        Color(0xFFEECDA1)
                                                                    )

                                                                    true -> listOf(
                                                                        Color(0xFF2C67F2),
                                                                        Color(0xFF62CFF4)
                                                                    )

                                                                    else -> {
                                                                        listOf(
                                                                            Color.Gray,
                                                                            Color.DarkGray
                                                                        )
                                                                    }
                                                                }
                                                            ),
                                                            shape = RoundedCornerShape(10.dp)
                                                        )
                                                        .clip(
                                                            shape = RoundedCornerShape(10.dp)
                                                        )
                                                ) {
                                                    Row(
                                                        modifier = Modifier.fillMaxSize(),
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
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
                                                        when (el.genderIsMan) {
                                                            true -> Icon(
                                                                painter = painterResource(R.drawable.baby_boy_face),
                                                                null,
                                                                tint = Color(0x66FFFFFF)
                                                            )

                                                            false -> Icon(
                                                                painter = painterResource(R.drawable.baby_girl_face),
                                                                null,
                                                                tint = Color(0x66FFFFFF)
                                                            )

                                                            else -> {}
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    item {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.spacedBy(5.dp)
                                        ) {
                                            Text("Сеансы пользователя:")
                                            for (el in listRecordsWithId) {
                                                if (el.second.user == showUser!!.first) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(180.dp, 100.dp)
                                                            .background(
                                                                brush = Brush.linearGradient(
                                                                    colors = when (el.second.state) {
                                                                        Record.State.NotYet -> listOf(
                                                                            Color.Gray,
                                                                            Color.LightGray
                                                                        )
                                                                        Record.State.Сonducted -> listOf(
                                                                            Color(0xFF49C628),
                                                                            Color(0xFF70F570)
                                                                        )
                                                                        Record.State.Cancelled -> listOf(
                                                                            Color(0xFFFF3300),
                                                                            Color(0xFFFF8800)
                                                                        )
                                                                    }
                                                                ),
                                                                shape = RoundedCornerShape(10.dp)
                                                            )
                                                            .clip(
                                                                shape = RoundedCornerShape(10.dp)
                                                            )
                                                            .padding(if (el.second.state == Record.State.NotYet) 0.dp else 5.dp)
                                                            .clickable {
                                                                isShowRecord = true
                                                                showRecord = el
                                                            }
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .fillMaxSize(),
                                                        ) {
                                                            Text("${el.second.time.toDate().date}.${el.second.time.toDate().month + 1}.${el.second.time.toDate().year + 1900}")
                                                            Text("${el.second.time.toDate().hours}:${el.second.time.toDate().minutes}${if (el.second.time.toDate().minutes == 0) "0" else ""}")
                                                            Text(
                                                                text = when (el.second.state) {
                                                                    Record.State.NotYet -> "Ещё не состоялся"
                                                                    Record.State.Сonducted -> "Проведён"
                                                                    Record.State.Cancelled -> "Отменён"
                                                                },
                                                                fontSize = 30.sp,
                                                                color = Color(0x66FFFFFF),
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            onDismissRequest = { isShowUser = false },
                            confirmButton = {
                                Button(
                                    onClick = { isShowUser = false }
                                ) {
                                    Text("Ок")
                                }
                            }
                        )
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
                                }
                            }
                        )
                    }
                    // endregion
                }
            } else {
                // region Editing Mode
                var isSave by remember { mutableStateOf(false) }
                var intermediateImage by remember { mutableIntStateOf(person.image) }
                var intermediateSurname by remember { mutableStateOf(person.surname) }
                var intermediateName by remember { mutableStateOf(person.name) }
                var intermediatePatronymiс by remember { mutableStateOf(person.patronymiс) }
                var intermediateBirthday by remember { mutableStateOf(person.birthday) }
                var intermediateMail by remember { mutableStateOf(auth.currentUser!!.email) }
                var intermediateTelephone by remember { mutableStateOf(person.telephoneNumber) }
                var intermediateGender by remember { mutableStateOf(person.genderIsMan) }
                var intermediateDescription by remember { mutableStateOf(person.description) }
                var isLogOut by remember { mutableStateOf(false) }
                var isDeleteAccount by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (
                                intermediateImage != person.image ||
                                intermediateSurname != person.surname ||
                                intermediateName != person.name ||
                                intermediatePatronymiс != person.patronymiс ||
                                intermediateMail != auth.currentUser!!.email ||
                                intermediateTelephone != person.telephoneNumber ||
                                intermediateBirthday != person.birthday ||
                                intermediateGender != person.genderIsMan ||
                                intermediateDescription != person.description
                            ) {
                            isSave = true
                            } else {
                                isEditingMode = false
                            }
                        },
                            modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Text(
                            "Завершить редактирование",
                            color = Color.Blue
                        )
                    }
                }
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(intermediateImage),
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
                                                                if (el == intermediateImage) {
                                                                    MaterialTheme.colorScheme.outline
                                                                } else {
                                                                    Color.Transparent
                                                                },
                                                                CircleShape
                                                            )
                                                            .clickable {
                                                                intermediateImage = el
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
                            val datePickerState = rememberDatePickerState()
                            val confirmEnabled = remember {
                                derivedStateOf { datePickerState.selectedDateMillis != null }
                            }
                            DatePickerDialog(
                                onDismissRequest = {
                                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                                    // button. If you want to disable that functionality, simply use an empty
                                    // onDismissRequest.
                                    showDatePicker = false
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            intermediateBirthday = Timestamp(Date(datePickerState.selectedDateMillis!!))
                                            showDatePicker = false
                                        },
                                        enabled = confirmEnabled.value
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            intermediateBirthday?.toDate()?.let {
                                Text(
                                    "Дата рождения: ${it.date}.${it.month.plus(1)}.${it.year.plus(1900)}"
                                )
                            }
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
                                onClick = {
                                    isLogOut = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF9800)
                                )
                            ) {
                                Text("Выйти из аккаунта")
                            }
                            Button(
                                onClick = {
                                    isDeleteAccount = true
                                          },
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
                                        person.image = intermediateImage
                                        person.surname = intermediateSurname
                                        person.name = intermediateName
                                        person.patronymiс = intermediatePatronymiс
                                        person.telephoneNumber = intermediateTelephone
                                        person.birthday = intermediateBirthday
                                        person.description = intermediateDescription
                                        person.genderIsMan = intermediateGender

                                        auth.currentUser!!.updateEmail(intermediateMail!!)
                                        users.document(auth.uid!!)
                                            .set(person)
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
                if (isLogOut) {
                    AlertDialog(
                        title = { Text("Вы уверены, что хотите выйти из аккаунта?") },
                        text = {},
                        onDismissRequest = { isLogOut = false },
                        dismissButton = {
                            Button(
                                onClick = { isLogOut = false }
                            ) {
                                Text("Нет")
                            }
                                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    currentPerson = null
                                    auth.signOut()
                                    GlobalScope.launch {
                                        snackbarHostState.showSnackbar("Вы успешно вышли из аккаунта")
                                    }
                                    navController.navigate(NavRoutes.Account.route)
                                }
                            ) {
                                Text("Да")
                            }
                        }
                    )
                }
                if (isDeleteAccount) {
                    var isSuccessful : Boolean? by remember { mutableStateOf(null) }
                    AlertDialog(
                        title = { Text("Вы уверены, что хотите удалить аккаунт?") },
                        text = {
                            isSuccessful?.let {
                                if (it) {
                                    CircularProgressIndicator()
                                    navController.navigate(NavRoutes.Account.route)
                                } else {
                                    Text("При удалении аккаунта произошля какая-то ошибка, попробуте ещё раз")
                                }
                            }
                        },
                        onDismissRequest = { isDeleteAccount = false },
                        dismissButton = {
                            Button(
                                onClick = { isDeleteAccount = false }
                            ) {
                                Text("Нет")
                            }
                                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    GlobalScope.launch {
                                        isSuccessful = deleteCurrentAccount(auth, users)
                                    }
                                }
                            ) {
                                Text("Да")
                            }
                        }
                    )
                }
                // endregion
            }
        } ?: run {
            // region Не вошёл
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "Что-бы просматривать информацию об аккаунте, необходимо войти или зарегистроваться:",
                    textAlign = TextAlign.Center
                )
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
            // endregion
        }
    }
}



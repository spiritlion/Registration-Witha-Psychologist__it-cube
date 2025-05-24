package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun TestingScreen(navController : NavHostController, modifier: Modifier = Modifier, auth: FirebaseAuth, db : FirebaseFirestore, records : CollectionReference) {
    Column {
        Text("Ещё не реализованные или ненужные функции")
        LazyColumn {
            item {
            /*
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

             */
            }
            item {


                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Creating a button that on
                    // click displays/shows the DatePickerDialog
                    Button(
                        onClick = {
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFF0F9D58)
                        )
                    ) {
                        Text(text = "Open Date Picker", color = Color.White)
                    }

                    // Adding a space of 100dp height
                    Spacer(modifier = Modifier.size(100.dp))

                    // Displaying the mDate value in the Text
                    Text(
                        text = "Selected Date: ",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            item {
                Button(
                    {
                        navController.navigate(NavRoutes.Log.route)
                    }
                ) {
                    Text("Войти")
                }
            }
            item {
                Button(
                    {
                        navController.navigate(NavRoutes.Registration.route)
                    }
                ) {
                    Text("Зарегистрироваться")
                }
            }
            item {
                var isOpenDialog by remember { mutableStateOf(false) }
                var selectedDay by remember { mutableStateOf(LocalDate.now()) }
                var selectedTime by remember { mutableStateOf(LocalTime.now().plusHours(1)) }
                Text("current Date: $selectedDay $selectedTime")
                Button( {isOpenDialog = true} ) {  }
                if (isOpenDialog) {
                    val pagerState = rememberPagerState(pageCount = { 5 })
                    var currentMonth = selectedDay.month
                    var currentYear = selectedDay.year
                    var dayOfMonth = currentMonth.length(
                        if (currentYear % 400 == 0) { true }
                        else if (currentYear % 100 == 0) { false }
                        else if (currentYear % 4 == 0) { true }
                        else { false }
                    )
                    AlertDialog(
                        title = {
                            Text("current Date: $selectedDay $selectedTime") },
                        text = {
                            HorizontalPager(state = pagerState) { page ->
                               // var i = page * 7 + 1
                                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                                    for (j in 1..5) {
                                        val i = page * 5 + j
                                        if (i <= dayOfMonth) {
                                            Column(
                                                modifier = Modifier
                                                    .border(
                                                        width = if (i == LocalDate.now().dayOfMonth) {
                                                            1.dp
                                                        } else {
                                                            0.dp
                                                        },
                                                        color = if (i == LocalDate.now().dayOfMonth) {
                                                            Color.Blue
                                                        } else {
                                                            Color.Transparent
                                                        }
                                                    )
                                            ) {
                                                Text("$i ")
                                                Text(when (j) {
                                                    1 -> "ПН"
                                                    2 -> "ВТ"
                                                    3 -> "СР"
                                                    4 -> "ЧТ"
                                                    5 -> "ПТ"
//                                                    6 -> "СБ"
//                                                    7 -> "ВС"
                                                    else -> ""
                                                })
                                                for (el in listOf(
                                                    8,
                                                    9,
                                                    10,
                                                    11,
                                                    12,
                                                    13,
                                                    14,
                                                    15,
                                                    16,
                                                    17
                                                )) {
                                                    Text(
                                                        text = "$el:00",
                                                        modifier = Modifier
                                                            .clickable{
                                                                selectedDay = LocalDate.of(
                                                                    /* year = */ currentYear,
                                                                    /* month = */ currentMonth,
                                                                    /* dayOfMonth = */ i
                                                                )
                                                                selectedTime = LocalTime.of(
                                                                    /* hour = */ el,
                                                                    /* minute = */ 0
                                                                )
                                                            }
                                                            .border(
                                                                1.dp,
                                                                if (
                                                                    currentYear == selectedDay.year &&
                                                                    currentMonth == selectedDay.month &&
                                                                    i == selectedDay.dayOfMonth &&
                                                                    el == selectedTime.hour
                                                                ) {
                                                                    Color.Blue
                                                                } else {
                                                                    Color.Transparent
                                                                }
                                                            )
                                                    )
                                                }
                                            }
                                            //i ++
                                        } else break
                                    }
                                }
                            }

                        },
                        onDismissRequest = { isOpenDialog = false },
                        confirmButton = {  }
                    )
                }
            }
            item {
                CalendarView()
            }
            item {
                val currentDate = LocalDate.now()
                var currentMonth by remember { mutableIntStateOf(currentDate.monthValue) }
                var currentYear by remember { mutableIntStateOf(currentDate.year) }
                val daysInMonth = LocalDate.of(currentYear, currentMonth, 1).lengthOfMonth()
                val firstDayOfWeek = LocalDate.of(currentYear, currentMonth, 1).dayOfWeek.value % 7 // Пн=0, Вт=1, ..., Вс=6
                val pagerState = rememberPagerState(pageCount = {(daysInMonth + firstDayOfWeek + 6) / 7})
                HorizontalPager(
                    state = pagerState
                ) { page ->
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (day in 0 until 7) {
                                val dayIndex = page * 7 + day - firstDayOfWeek
                                if (dayIndex in 1..daysInMonth) {
                                    val isToday = dayIndex == currentDate.dayOfMonth && currentMonth == currentDate.monthValue && currentYear == currentDate.year
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .border(
                                                width = if (isToday) 2.dp else 0.dp,
                                                color = if (isToday) Color.Blue else Color.Transparent
                                            )
                                            .padding(8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dayIndex.toString(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    Spacer(modifier = Modifier.weight(1f)) // Пустое пространство для дней вне месяца
                                }
                            }
                        }
                        // Здесь можно добавить дополнительные элементы, такие как время и т.д.
                    }
                }
            }
        }
    }
}

/*
@Composable
fun CustomCalendar() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.Center,
    ) {
        item {

        }
        for (el1 in calendarDate) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(el1.key)
                    Text("--")
                    for (el2 in el1.value) {
                        Text("${el2[0]}:${if (el2[1] == 0) "00" else el2[1]}", modifier = Modifier.clickable {  })
                    }
                }
            }
        }
        item {

            item {
                var mail by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                Column {
                    TextField(
                        value = mail,
                        onValueChange = { mail = it }
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it}
                    )
                    Button(
                        {
                            auth.createUserWithEmailAndPassword(mail, password)
                                .addOnCompleteListener(LocalContext) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success")
                                        val user = auth.currentUser
                                        // updateUI(user)
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.exception)

                                        // updateUI(null)
                                    }
                                }
                        }
                    ) {
                        Text("Зарегистрироваться")
                    }
                }
            }
        }
    }
}

 */

@Composable
fun CalendarView() {
    var currentMonth by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Заголовок с месяцем и навигацией
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")))
            Row {
                Text(
                    text = "<",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable{ currentMonth = currentMonth.minusMonths(1) }
                )
                Text(
                    text = ">",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable{ currentMonth = currentMonth.plusMonths(1) }
                )
            }
        }

        // Дни недели
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            for (day in daysOfWeek) {
                BasicText(text = day)
            }
        }

        // Дни месяца
        val firstDayOfMonth = currentMonth.withDayOfMonth(1)
        val lastDayOfMonth = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth())

        // Смещение для первого дня месяца
        val startOffset = firstDayOfMonth.dayOfWeek.value % 7

        // Создаем сетку дней месяца
        Column {
            var dayCounter = 1

            for (week in 0..5) { // максимум 6 недель в месяце
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (day in 0..6) {
                        if (week == 0 && day < startOffset) {
                            // Пустые ячейки до первого дня месяца
                            Spacer(modifier = Modifier.weight(1f))
                        } else if (dayCounter <= lastDayOfMonth.dayOfMonth) {
                            BasicText(
                                text = dayCounter.toString(),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            dayCounter++
                        } else {
                            // Пустые ячейки после последнего дня месяца
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}


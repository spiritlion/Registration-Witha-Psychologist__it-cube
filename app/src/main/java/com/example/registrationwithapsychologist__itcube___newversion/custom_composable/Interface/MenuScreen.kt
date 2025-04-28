package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import java.time.LocalDate
import java.time.LocalTime

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MenuScreen(navController : NavHostController, modifier: Modifier = Modifier) {
    if (currentPerson != null) {

        var reason by remember { mutableStateOf("") }

        // Declaring integer values
        // for year, month and day

        var selectedDay : LocalDate? by remember { mutableStateOf(null) }
        var selectedTime : LocalTime? by remember { mutableStateOf(null) }
        // Declaring a string value to
        // store date in string format

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        var isShowCalendar by remember { mutableStateOf(false) }

        var isIRecording by remember { mutableStateOf(false) }
        var whoFromBabyIsRecording = remember { mutableStateOf(mutableMapOf<PersonData.BabyData, Boolean>()) }
        for (el in currentPerson?.children ?: listOf<PersonData.BabyData>()) {
            whoFromBabyIsRecording.value[el] = false
        }
        Row {
            Spacer(
                modifier = Modifier
                    .padding(10.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Запись",
                    fontSize = 40.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier.padding(20.dp)
                )
                Row {
                    Text(
                        text = "Дата: ${if (selectedDay == null && selectedTime == null) "Дата не выбрана" else "$selectedDay $selectedTime"} ",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Button( { isShowCalendar = true } ) {
                        Text("Выбрать дату время записи")
                    }
                }
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Кто идёт: ",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Column {
                        Row {
                            Text("${currentPerson!!.surname!!} ${currentPerson!!.name!!} ${currentPerson!!.patronymiс!!} (Вы)", modifier = Modifier.weight(1f))
                            Checkbox(
                                checked = isIRecording,
                                onCheckedChange = { isIRecording = it },
                                modifier = Modifier
                                    .width(50.dp)
                            )
                        }
                        for (el in whoFromBabyIsRecording.value) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text("${el.key.surname} ${el.key.name} ${el.key.patronymiс}", modifier = Modifier.weight(1f))
                                Checkbox(
                                    checked = el.value,
                                    onCheckedChange = {
                                        val intermediate : MutableMap<PersonData. BabyData, Boolean> = mutableMapOf()
                                        for (el in whoFromBabyIsRecording.value) {
                                            intermediate[el.key] = el.value
                                        }// пройтись циклом
                                        intermediate[el.key] = it
                                        whoFromBabyIsRecording = mutableStateOf(intermediate)
                                    },
                                    modifier = Modifier
                                        .width(50.dp)
                                )
                            }
                        }
                    }
                }
                TextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text("Причина") },
                    placeholder = { Text("Почему вы решили записаться") }
                )
                Spacer(
                    modifier = Modifier.padding(20.dp)
                )
                Button(
                    onClick = {  },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Записаться")
                }
            }
        }
        if (isShowCalendar) {
            val pagerState = rememberPagerState(pageCount = { 5 })
            var currentMonth = selectedDay?.month ?: LocalDate.now().month
            var currentYear = selectedDay?.year ?: LocalDate.now().year
            var dayOfMonth = currentMonth?.length(
                if (currentYear % 400 == 0) { true }
                else if (currentYear % 100 == 0) { false }
                else if (currentYear % 4 == 0) { true }
                else { false }
            ) ?: LocalDate.now().dayOfMonth
//            var newSelectedDay by remember { ... }
//            var newSelectedTime by remember { ... }
            AlertDialog(
                title = {
                    Text("Выбор даты") },
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
                                                            currentYear == selectedDay?.year &&
                                                            currentMonth == selectedDay?.month &&
                                                            i == selectedDay?.dayOfMonth &&
                                                            el == selectedTime?.hour
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
                onDismissRequest = { isShowCalendar = false },
                confirmButton = {
                    Button(
                        onClick = {

                            isShowCalendar = false
                        }
                    ) {
                        Text("Подтвердить")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            isShowCalendar = false
                        }
                    ) {
                        Text("Отмена")
                    }
                }
            )
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            Text("Что-бы записаться к психологу, необходимо войти или зарегистроваться:", textAlign = TextAlign.Center)
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


//@Composable
//@Preview
//fun MenuScreenPreview() {
//    MenuScreen()
//}
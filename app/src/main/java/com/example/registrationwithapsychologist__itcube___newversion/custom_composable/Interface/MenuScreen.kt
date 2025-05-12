package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.MainActivity
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts.Record
import com.example.registrationwithapsychologist__itcube___newversion.recordDate
import com.google.android.play.core.integrity.d
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MenuScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    records: CollectionReference,
    auth: FirebaseAuth
) {
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
            whoFromBabyIsRecording.value[el] = true
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
                    onClick = {
                        var whoFromBabyIsRecordingList = mutableListOf<PersonData.BabyData>()
                        for (el in whoFromBabyIsRecording.value) {
                            if (el.value) whoFromBabyIsRecordingList.add(el.key)
                        }
                        GlobalScope.launch {
                            recordDate(
                                records,
                                record = Record(
                                    time = Timestamp(
                                        Date(
                                            /* year = */ (selectedDay?.year?.minus(1900)) ?: (LocalDate.now().year - 1900),
                                            /* month = */ (selectedDay?.month?.value?.minus(1)) ?: LocalDate.now().month.value,
                                            /* date = */ selectedDay?.dayOfMonth ?: LocalDate.now().dayOfMonth,
                                            /* hrs = */ selectedTime?.hour ?: (LocalTime.now().hour + 1),
                                            /* min = */ 0
                                        )
                                    ),
                                    user = auth.uid ?: "",
                                    psychologist = "",
                                    reason = reason,
                                    isUserRecording = isIRecording,
                                    whoFromBabyIsRecording = whoFromBabyIsRecordingList
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Записаться")
                }
            }
        }
        if (isShowCalendar) {
            var currentMonth = selectedDay?.month ?: LocalDate.now().month
            var currentYear = selectedDay?.year ?: LocalDate.now().year
            var dayOfMonth = currentMonth?.length(
                if (currentYear % 400 == 0) { true }
                else if (currentYear % 100 == 0) { false }
                else if (currentYear % 4 == 0) { true }
                else { false }
            ) ?: LocalDate.now().dayOfMonth
            var newSelectedDay by remember { mutableStateOf(selectedDay) }
            var newSelectedTime by remember { mutableStateOf(selectedTime) }

            var showDataPickerDialog by remember { mutableStateOf(false) }


            val daysInMonth = LocalDate.of(currentYear, currentMonth, 1).lengthOfMonth()
            val firstDayOfWeek = LocalDate.of(currentYear, currentMonth, 1).dayOfWeek.value % 7 // Пн=0, Вт=1, ..., Вс=6
            val pagerState = rememberPagerState(pageCount = {(daysInMonth + firstDayOfWeek + 6) / 7})
            AlertDialog(
                title = {
                    Column {
                        Text("Выбор даты")
                        Text("Предвадительый выбор: ${if (newSelectedDay == null && newSelectedTime == null) "Дата не выбрана" else "$newSelectedDay $newSelectedTime"}", fontSize = 10.sp)
                    }
                        },
                text = {
                    Column {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Icon(
                                    Icons.Filled.DateRange,
                                    null,
                                    modifier = Modifier
                                        .clickable{
                                            showDataPickerDialog = true
                                        }
                                )
                                Text("$currentYear ${currentMonth.name}")
                            }
                            Row {
                                IconButton(
                                    onClick = {
                                        currentMonth + 1
                                    }
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        null
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        currentMonth - 1
                                    }
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        null
                                    )
                                }
                            }
                        }
                        HorizontalPager(
                                state = pagerState
                                ) { page ->
                            Column {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                    for (day in 1 .. 5) {
                                        val dayIndex = page * 5 + day - firstDayOfWeek
                                        val isToday = dayIndex == LocalDate.now().dayOfMonth && currentMonth.value == LocalDate.now().monthValue && currentYear == LocalDate.now().year
                                        Column(modifier = Modifier
                                            .border(
                                                width = if (isToday) 2.dp else 0.dp,
                                                color = if (isToday) Color.Blue else Color.Transparent
                                            )
                                        ){
                                            Text(
                                                text = when (day) {
                                                    1 -> "Пн"
                                                    2 -> "Вт"
                                                    3 -> "Ср"
                                                    4 -> "Чт"
                                                    5 -> "Пт"
                                                    else -> ""
                                                }
                                            )
                                            if (dayIndex in 1..daysInMonth) {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(8.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = dayIndex.toString()
                                                    )
                                                }
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
                                                                newSelectedDay = LocalDate.of(
                                                                    /* year = */ currentYear,
                                                                    /* month = */ currentMonth,
                                                                    /* dayOfMonth = */ dayIndex
                                                                )
                                                                newSelectedTime = LocalTime.of(
                                                                    /* hour = */ el,
                                                                    /* minute = */ 0
                                                                )
                                                            }
                                                            .border(
                                                                1.dp,
                                                                if (
                                                                    currentYear == newSelectedDay?.year &&
                                                                    currentMonth == newSelectedDay?.month &&
                                                                    dayIndex == newSelectedDay?.dayOfMonth &&
                                                                    el == newSelectedTime?.hour
                                                                ) {
                                                                    Color.Blue
                                                                } else {
                                                                    Color.Transparent
                                                                }
                                                            )
                                                    )
                                                }
                                            } else {
                                                Spacer(modifier = Modifier.weight(1f)) // Пустое пространство для дней вне месяца
                                            }
                                        }
                                    }
                                }
                                // Здесь можно добавить дополнительные элементы, такие как время и т.д.
                            }
                        }
                    }
                    if (showDataPickerDialog) {
                        val datePickerState = rememberDatePickerState()
                        val confirmEnabled = remember {
                            derivedStateOf { datePickerState.selectedDateMillis != null }
                        }
                        DatePickerDialog(
                            onDismissRequest = {
                                // Dismiss the dialog when the user clicks outside the dialog or on the back
                                // button. If you want to disable that functionality, simply use an empty
                                // onDismissRequest.
                                showDataPickerDialog = false
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        var date = Date(datePickerState.selectedDateMillis!!)
                                        currentYear = date.year + 1900
                                        currentMonth = LocalDate.of(0, date.month + 1, 1).month
                                        showDataPickerDialog = false
                                    },
                                    enabled = confirmEnabled.value
                                ) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDataPickerDialog = false }) { Text("Cancel") }
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
                },
                onDismissRequest = { isShowCalendar = false },
                confirmButton = {
                    Button(
                        onClick = {
                            selectedDay = newSelectedDay
                            selectedTime = newSelectedTime
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

package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.NavRoutes
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MenuScreen(navController : NavHostController, modifier: Modifier = Modifier) {
    if (currentPerson != null) {

        var reason by remember { mutableStateOf("") }

        val mContext = LocalContext.current

        // Declaring integer values
        // for year, month and day
        val mYear: Int = LocalDate.now().year
        val mMonth: Int = LocalDate.now().month.value
        val mDay: Int = LocalDate.now().dayOfMonth
        val mHour: Int = LocalTime.now().hour + 1
        val mMinute: Int = LocalTime.now().minute

        // Declaring a string value to
        // store date in string format
        val mDate = remember { mutableStateOf("") }

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
                        text = "Дата: ${mDate.value} ",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Button( { isShowCalendar = true } ) {
                        Text("Выбрать дату")
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
            AlertDialog(
                onDismissRequest = { isShowCalendar = false },
                title = { Text("Выбор даты") },
                text = {
                    var dataChoose by remember { mutableStateOf(true) }
                    Column {
                        Text("Выбраная дата: $mDay.$mMonth.$mYear $mHour:$mMinute")
                        Button(
                            onClick = { dataChoose = true }
                        ) {
                            Text("")
                        }
                        val datePickerState = rememberDatePickerState()
                        if (dataChoose) {
                            val snackState = remember { SnackbarHostState() }
                            val snackScope = rememberCoroutineScope()
                            SnackbarHost(hostState = snackState, Modifier)
                            // TODO demo how to read the selected date from the state.
                            val confirmEnabled = remember {
                                derivedStateOf { datePickerState.selectedDateMillis != null }
                            }
                            DatePickerDialog(
                                onDismissRequest = {
                                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                                    // button. If you want to disable that functionality, simply use an empty
                                    // onDis dataChoose= false
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                             dataChoose= false
                                            snackScope.launch {
                                                snackState.showSnackbar(
                                                    "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                                                )
                                            }
                                        },
                                        enabled = confirmEnabled.value
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { dataChoose = false }) { Text("Cancel") }
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
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(5)
                            ) {
                                for (el in listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17)) {
                                    item {  }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {

                        },
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
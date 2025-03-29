package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import java.util.Calendar
import java.util.Date

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    var reason by remember { mutableStateOf("") }

    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )


    var isIRecording by remember { mutableStateOf(false) }
    var whoFromBabyIsRecording = remember { mutableStateOf(mutableMapOf<PersonData.BabyData, Boolean>()) }
    for (el in currentPerson.children) {
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
                Button( { mDatePickerDialog.show() } ) {
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
                        Text("${currentPerson.surname} ${currentPerson.name} ${currentPerson.patronymiс} (Вы)", modifier = Modifier.weight(1f))
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
}


@Composable
@Preview
fun MenuScreenPreview() {
    MenuScreen()
}
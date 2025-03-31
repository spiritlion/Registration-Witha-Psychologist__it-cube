package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.LogUser
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun TestingScreen(modifier: Modifier = Modifier) {
    Column {
        Text("Ещё не реализованные или ненужные функции")
        LazyColumn {
            item {
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
            }
            item {
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

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Creating a button that on
                    // click displays/shows the DatePickerDialog
                    Button(
                        onClick = {
                            mDatePickerDialog.show()
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
                        text = "Selected Date: ${mDate.value}",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
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
                        onValueChange = { password = it }
                    )
                    Button(
                        {
                            GlobalScope.launch {
                                LogUser(mail, password)
                            }
                        }
                    )
                    {
                        Text("Войти")
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
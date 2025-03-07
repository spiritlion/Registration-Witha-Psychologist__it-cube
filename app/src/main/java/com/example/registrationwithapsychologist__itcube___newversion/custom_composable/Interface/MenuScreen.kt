package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.example.registrationwithapsychologist__itcube___newversion.R
import com.example.registrationwithapsychologist__itcube___newversion.currentPerson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

var isRegistration = mutableStateOf(false)
@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    val date = LocalDate.now()
    val currentDate by remember { mutableStateOf(date) }
    var scale by remember { mutableIntStateOf(1) }
    Column(modifier = modifier) {
        Row (horizontalArrangement = Arrangement.Center, modifier = Modifier
            .padding()
            .fillMaxWidth()) {
            Text("$currentDate")
        }
        Button(onClick = { if (scale < 3) {scale += 1}  },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = when (scale) {
                    1 -> "Неделя"
                    2 -> "Месяц"
                    3 -> "Год"
                    else -> "Error"
                },
                textDecoration = TextDecoration.Underline
            )

        }
        when(scale) {
            1 -> PrintTableWeek()
            2 -> PrintTableMonth(currentDate)
            3 -> PrintTableYear(currentDate)
            else -> {
                Text(text = "Error")
            }
        }
        if (isRegistration.value) {
            PrintRegistration()
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@Composable
fun PrintRegistration() {
    var startRegistration by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = {  },
        title = { Text("Запись на <Выбранная дата>") },
        text = {
            if (!startRegistration) {
                var isIRecording by remember { mutableStateOf(false) }
                val whoFromBabyIsRecording = remember { mutableStateOf(mutableMapOf<PersonData.BabyData, Boolean>()) }

                if (currentPerson.childrens.isNotEmpty()) {
                    for (el in currentPerson.childrens) {
                        whoFromBabyIsRecording.value[el] = false
                    }
                }
                LazyColumn {
                    item {
                        Row {
                            Checkbox(
                                checked = isIRecording,
                                onCheckedChange = { isIRecording = it }
                            )
                            Text("Себя", modifier = Modifier.padding(vertical = 13.dp))
                        }
                    }
                    for ((key, value) in whoFromBabyIsRecording.value) {
                        item(key.hashCode().toString()) { // Уникальный ключ для каждого элемента
                            Row {
                                Checkbox(
                                    checked = value,
                                    onCheckedChange = {
                                        whoFromBabyIsRecording.value[key] = it // Обновляем значение в карте
                                    }
                                )
                                Text(key.name, modifier = Modifier.padding(vertical = 13.dp))
                            }
                        }
                    }
                }


            } else {
                var progress by remember { mutableFloatStateOf(0.0f) }
                val scope = rememberCoroutineScope()
                scope.launch {
                    while (progress < 1f) {
                        progress += 0.01f
                        delay(1000L)
                    }
                    if (progress > 1f) progress = 1f
                }
                CircularProgressIndicator(progress = progress, color = Color(0f, if (progress > 1f) 1f else progress, 0f))
                val startOffset = 300   // начальная позиция
                val endOffset = 60  // конечная позиция
                val boxWidth = 150      // ширина компонента

                var boxState by remember { mutableIntStateOf(startOffset) }
                val offset by animateDpAsState(targetValue = boxState.dp)

                if (progress >= 1f) boxState = endOffset

                Column(Modifier.fillMaxWidth()) {
                    Box(Modifier
                        .padding(start = offset)
                        .size(boxWidth.dp)) {
                        Text("Запись завершена.\nСпасибо, что выбрали нас)",
                            color = Color.Green,
                            fontSize = 25.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (progress == 1f) {
                        Button({ isRegistration.value = false }, modifier = Modifier.align(Alignment.End)) {
                            Text("Ок")
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (!startRegistration) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            isRegistration.value = false
                        }
                    ) {
                        Text("Отмена")
                    }
                    Row {
                        Button(
                            onClick = {
                                startRegistration = true
                            }
                        ) {
                            Text("Записаться")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PrintTableWeek() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier.padding(30.dp),

    ) {
        for (el in listOf(
            "ПН",
            "ВТ",
            "СР",
            "ЧТ",
            "ПН")
        ) {
            item {
                Text(text = el,
                    textAlign = TextAlign.Center)
            }
        }
        for (el in listOf(
            "8:00",
            "9:00",
            "10:00",
            "11:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00"
        )
        ) {
            for (i in 1..5) {
                item {
                    Box(
                        modifier = Modifier
                            .clickable {
                                isRegistration.value = true
                            }
                            .background(Color.LightGray)
                            .border(1.dp, Color.Gray)
                            .padding(10.dp)
                            .size(41.dp)
                    ) {
                        Text(text = el,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun PrintTableMonth(currentDate: LocalDate) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier.padding(30.dp),
    ) {
        for (el in listOf(
            "ПН",
            "ВТ",
            "СР",
            "ЧТ",
            "ПН"
        )
        ) {
            item {
                Text(
                    text = el,
                    textAlign = TextAlign.Center
                )
            }
        }
        for (i in 1..when (currentDate.monthValue) {
            1 -> 31
            2 -> 28
            3 -> 31
            4 -> 30
            5 -> 31
            6 -> 30
            7 -> 31
            8 -> 31
            9 -> 30
            10 -> 31
            11 -> 30
            12 -> 31
            else -> 1
        }) {
            item {
                Box(
                    modifier = Modifier
                        .clickable { }
                        .background(Color.LightGray)
                        .border(1.dp, Color.Gray)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "$i",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun PrintTableYear(currentDate: LocalDate) {
    Text(text = "${LocalDate.now().year}",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    LazyColumn (
        modifier = Modifier.padding(30.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for ((i, el) in listOf(
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
        ).withIndex()
        ) {
            item {
                if (i == 5) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = null
                        )
                        Box(
                            modifier = Modifier
                                .clickable {
                                    currentDate.withMonth(
                                        when (el) {
                                            "Январь" -> 1
                                            "Февраль" -> 2
                                            "Март" -> 3
                                            "Апрель" -> 4
                                            "Май" -> 5
                                            "Июнь" -> 6
                                            "Июль" -> 7
                                            "Август" -> 8
                                            "Сентябрь" -> 9
                                            "Октябрь" -> 10
                                            "Ноябрь" -> 11
                                            "Декабрь" -> 12
                                            else -> 1
                                        }
                                    )
                                }
                                .background(Color.LightGray)
                                .border(1.dp, Color.Gray)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = el,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Image(
                            painter = painterResource(R.drawable.arrow_right),
                            contentDescription = null
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clickable {
                            currentDate.withMonth(
                                when (el) {
                                    "Январь" -> 1
                                    "Февраль" -> 2
                                    "Март" -> 3
                                    "Апрель" -> 4
                                    "Май" -> 5
                                    "Июнь" -> 6
                                    "Июль" -> 7
                                    "Август" -> 8
                                    "Сентябрь" -> 9
                                    "Октябрь" -> 10
                                    "Ноябрь" -> 11
                                    "Декабрь" -> 12
                                    else -> 1
                                }
                            )
                        }
                        .background(Color.LightGray)
                        .border(1.dp, Color.Gray)
                        .padding(10.dp)
                ) {
                    Text(
                        text = el,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}


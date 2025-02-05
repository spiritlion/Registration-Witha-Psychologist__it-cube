package com.example.registrationwithapsychologist__itcube.CustomComposable.Interface

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.registrationwithapsychologist__itcube.R
import java.time.LocalDate
import java.util.stream.IntStream.range

@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    val date = LocalDate.now()
    var currentDate by remember { mutableStateOf(date) }
    var scale by remember { mutableIntStateOf(1) }
    Column(modifier = modifier) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier,
//                    .size(75.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(horizontal = Dp.Hairline)
            ) {
                Image(painter = painterResource(R.drawable.account_image), contentDescription = null, modifier = Modifier.size(50.dp))
            }
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(horizontal = Dp.Hairline)
            ) {
                Image(painter = painterResource(R.drawable.settings_image), contentDescription = null, modifier = Modifier.size(50.dp))
            }
        }
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
            2 -> PrintTableMonth(currentDate, scale)
            3 -> PrintTableYear(currentDate)
            else -> {
                Text(text = "Error")
            }
        }
    }
}

@Composable
fun PrintTableWeek() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5)
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
                            .clickable { }
                            .background(Color.LightGray)
                            .border(1.dp, Color.Gray)
                            .padding(10.dp)
                    ) {
                        Text(text = el,
                            textAlign = TextAlign.Center)
                    }
                }
            }
        }

    }
}

@Composable
fun PrintTableMonth(currentDate: LocalDate, scale : Int) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5)
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(5)
    ) {
        for (el in listOf(
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
        )
        ) {
            item {
                Box(
                    modifier = Modifier
                        .clickable {
                            currentDate.withMonth(when (el) {
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
                                })
                        }
                        .background(Color.LightGray)
                        .border(1.dp, Color.Gray)
                        .padding(10.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "$el",
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}


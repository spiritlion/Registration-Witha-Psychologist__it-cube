package com.example.registrationwithapsychologist__itcube.custom_composable.Interface

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registrationwithapsychologist__itcube.R
import com.example.registrationwithapsychologist__itcube.currentPerson
import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData

@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                "Профиль",
                modifier = Modifier
                    .align(Alignment.Center),
                fontSize = 30.sp
            )
            Image(
                painter = painterResource(R.drawable.edit),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(35.dp)
            )
        }
        LazyColumn {
            item {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.account_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                    )
                    Text(currentPerson.surname)
                    Text(currentPerson.name)
                    Text(currentPerson.patronymiс)
                }
            }
            item {
                Text("Дата рождения: ${currentPerson.birthday}")
            }
            item {
                Text("Email: ${currentPerson.mail}")
            }
            item {
                Text("Пол: ${
                    when (currentPerson.gender) {
                        PersonData.Gender.Man -> "Мужской"
                        PersonData.Gender.Woman -> "Женский"
                    }
                }")
            }
            item {
                Text("О себе: ${currentPerson.description}")
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                    Text("Дети:")
                    for (el in currentPerson.childrens) {
                        Column (
                            modifier.border(1.dp, Color.LightGray)
                        ) {
                            Text(el.surname)
                            Text(el.name)
                            Text(el.patronymiс)
                            Text("Пол: ${
                                when (el.gender) {
                                    PersonData.Gender.Man -> "Мужской"
                                    PersonData.Gender.Woman -> "Женский"
                                }
                            }")
                        }
                    }
                    Row {
                        Image(
                            painter = painterResource(R.drawable.add),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Text("Добавить нового ребёнка")
                    }
                }

            }
        }
    }
}
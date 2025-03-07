package com.example.registrationwithapsychologist__itcube.custom_composable.Accounts

import android.telephony.PhoneNumberUtils
import java.time.LocalDate
import java.time.Month

var accounts = listOf(
    PersonData(
        surname = "<фамилия>",
        name = "<имя>",
        patronymiс = "<отчество>",
        telephoneNumber =  PhoneNumberUtils(),
        mail = "<mail>",
        password = "<пароль>",
        birthday = LocalDate.of(1999, Month.MAY, 11),
        gender = PersonData.Gender.Man,
        childrens = listOf(
            PersonData.BabyData(
                "<фамилия ребёнка 1>",
                "<имя ребёнка 1>",
                "<отчество ребёнка 1>",
                PersonData.Gender.Woman
            ),
            PersonData.BabyData(
                "<фамилия ребёнка 2>",
                "<имя ребёнка 2>",
                "<отчество ребёнка 2>",
                PersonData.Gender.Man
            ),
            PersonData.BabyData(
                "<фамилия ребёнка 3>",
                "<имя ребёнка 3>",
                "<отчество ребёнка 3>",
                PersonData.Gender.Woman
            )

        ),
        description = "Я есть..."
    )
)
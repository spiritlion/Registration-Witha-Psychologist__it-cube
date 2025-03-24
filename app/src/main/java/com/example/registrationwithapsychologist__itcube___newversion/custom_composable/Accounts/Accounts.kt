package com.example.registrationwithapsychologist__itcube.custom_composable.Accounts

import java.time.LocalDate
import java.time.Month

var accounts = mutableListOf(
    PersonData(
        surname = "<фамилия>",
        name = "<имя>",
        patronymiс = "<отчество>",
        telephoneNumber = "+71234567890",
        mail = "<mail>",
        password = "<пароль>",
        birthday = LocalDate.of(1999, Month.MAY, 11),
        gender = PersonData.Gender.Man,
        children = mutableListOf(
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
    ),
    PersonData(
        surname = "TODO()",
        name = "TODO()",
        patronymiс = "TODO()",
        telephoneNumber = "+81234567890",
        mail = "@",
        password = "rabbit",
        birthday = LocalDate.of(2025, 3, 23),
        gender = PersonData.Gender.Man,
        description = "TODO()"
    )
)
package com.example.registrationwithapsychologist__itcube.custom_composable

import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import java.time.LocalDate
import java.time.LocalTime

data class RecordData(
    var data : LocalDate,
    var time : LocalTime,
    var person : PersonData? = null
)
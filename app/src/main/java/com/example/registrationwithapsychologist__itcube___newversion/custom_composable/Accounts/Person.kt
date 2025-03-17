package com.example.registrationwithapsychologist__itcube.custom_composable.Accounts

import android.telephony.PhoneNumberUtils
import java.time.LocalDate


data class PersonData(
    var surname : String,
    var name : String,
    var patronymiс : String,
    var telephoneNumber : String,
    var mail : String,
    var password : String,
    var birthday : LocalDate,
    var gender : Gender,
    var children: MutableList<BabyData> = mutableListOf(),
    var description: String? = null
) {

    enum class Gender {
        Man,
        Woman
    }
    data class BabyData(
        var surname: String,
        var name: String,
        var patronymiс: String,
        var gender: Gender
    )
}

package com.example.registrationwithapsychologist__itcube.CustomComposable

import android.telephony.PhoneNumberUtils


data class Person(
    var surname : String,
    var name : String,
    var patronymiс : String,
    var telephoneNumber : PhoneNumberUtils,
    var mail : String,
    var password : String,
    var gender : Gender
) {
    enum class Gender {
        Man,
        Woman
    }
}

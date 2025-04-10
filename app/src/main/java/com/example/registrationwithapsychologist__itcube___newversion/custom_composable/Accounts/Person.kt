package com.example.registrationwithapsychologist__itcube.custom_composable.Accounts

import com.example.registrationwithapsychologist__itcube___newversion.R
import com.google.firebase.Timestamp
import java.time.LocalDate

data class PersonData(
    var surname : String? = null,
    var name : String? = null,
    var patronymiс : String? = null,
    var telephoneNumber : String? = null,
    var birthday : Timestamp? = null,
    var genderIsMan : Boolean? = null,
    var children: MutableList<BabyData>? = mutableListOf(),
    var image : Int? = R.drawable.avatar_base,
    var description: String? = null
) {
    data class BabyData(
        var surname: String? = null,
        var name: String? = null,
        var patronymiс: String? = null,
        var genderIsMan: Boolean? = null
    )
}

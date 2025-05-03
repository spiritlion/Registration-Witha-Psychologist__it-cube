package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts

import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.google.firebase.Timestamp

data class Record(
    var time: Timestamp = Timestamp.now(),
    var user: String = "",
    var psychologist: String = "",
    var state:  State = State.NotYet,
    var reason: String = "",
    var reasonForRefusal: String = "",
    var isUserRecording: Boolean = true,
    var whoFromBabyIsRecording: MutableList<PersonData.BabyData> = mutableListOf()
) {
    enum class State {
        NotYet,
        Сonducted,
        Cancelled,
    }
}

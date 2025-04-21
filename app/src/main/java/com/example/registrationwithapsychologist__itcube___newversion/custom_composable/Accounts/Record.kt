package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts

import com.google.firebase.Timestamp
import com.google.firebase.firestore.auth.User

data class Record(
    var time: Timestamp = Timestamp.now(),
    var user: String = "",
    var psychologist: String = "",
    var state:  State = State.NotYet,
    var reason: String = "",
    var reasonForRefusal: String = ""
) {
    enum class State {
        NotYet,
        Сonducted,
        Cancelled,
    }
}

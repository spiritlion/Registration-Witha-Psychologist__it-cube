package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts

import com.google.firebase.Timestamp
import com.google.firebase.firestore.auth.User

data class Record(
    var time: Timestamp = Timestamp.now(),
    var user: String = "",
    var psychologist: String = ""
)

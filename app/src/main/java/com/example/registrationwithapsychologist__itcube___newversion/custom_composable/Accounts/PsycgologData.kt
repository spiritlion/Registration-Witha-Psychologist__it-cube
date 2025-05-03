package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts

/**
 * Data класс психолога
 */
data class PsycgologData(
    /**
     * Id в Firebase firestore
     */
    var id : String = "",
    /**
     * Информация о психологе
     */
    var information : String = ""
)

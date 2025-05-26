package com.example.registrationwithapsychologist__itcube.custom_composable.Accounts

import com.example.registrationwithapsychologist__itcube___newversion.R
import com.google.firebase.Timestamp
import java.time.LocalDate

/**
 * Data класс пользователя
 */
data class PersonData(
    /**
     * Фамилия пользователя
     */
    var surname : String? = null,
    /**
     * Имя пользователя
     */
    var name : String? = null,
    /**
     * Отчество пользователя
     */
    var patronymiс : String? = null,
    /**
     * номер телефона пользователя
     */
    var telephoneNumber : String? = null,
    /**
     * День рождения пользователя
     */
    var birthday : Timestamp? = null,
    /**
     * Пол пользователя:
     * `true` - Мужской:
     * `false` - Женский
     */
    var genderIsMan : Boolean? = null,
    /**
     * Список детей, класса [BabyData]
     */
    var children: MutableList<BabyData>? = mutableListOf(),
    /**
     * Иконка пользователя
     */
    var image : Int = R.drawable.avatar_base,
    /**
     * поле "О себе" пользователя
     */
    var description: String? = "null"
) {
    /**
     * Data класс ребёнка
     */
    data class BabyData(
        /**
         * Фамилия ребёнка
         */
        var surname: String? = null,
        /**
         * Имя ребёнка
         */
        var name: String? = null,
        /**
         * Отчество ребёнка
         */
        var patronymiс: String? = null,
        /**
         * Пол ребёнка:
         * `true` - Мужской:
         * `false` - Женский
         */
        var genderIsMan: Boolean? = null
    )
}

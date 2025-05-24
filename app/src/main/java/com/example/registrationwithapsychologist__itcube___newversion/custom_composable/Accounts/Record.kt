package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Accounts

import com.example.registrationwithapsychologist__itcube.custom_composable.Accounts.PersonData
import com.google.firebase.Timestamp

/**
 * Data класс записи
 */
data class Record(
    /**
     * Время записи
     */
    var time: Timestamp = Timestamp.now(),
    /**
     * Пользователь, который записался
     */
    var user: String = "",
    /**
     * Псисхолог, к которому записалис
     */
    var psychologist: String = "",
    /**
     * Состояние записи типа [State]
     */
    var state:  State = State.NotYet,
    /**
     * Причина, по которой пользователь решил записаться
     */
    var reason: String = "",
    /**
     * Причина отмены записи
     */
    var reasonForRefusal: String = "",
    /** 0 - никто не отменял;
        1 - отменил пользователь;
        2 - отменил психолог; **/
    var whoCanceled: Int = 0,
    /**
     * Записался, ли сам пользователь
     */
    var isUserRecording: Boolean = true,
    /**
     * Кого, из своих детей записал пользователь
     */
    var whoFromBabyIsRecording: MutableList<Int> = mutableListOf(),
    /**
     * Итог сеанса
     * (То, что видит психолог)
     */
    var result : String = "",
    /**
     *  Итог сеанса
     * (То, что видит пользователь)
     */
    var comment : String = ""
) {
    /**
     * [Enum] класс состояния записи, может принимать следующие значения: [NotYet], [Сonducted], [Cancelled]
     **/
    enum class State {
        /**
         * Ешё не проведена
         */
        NotYet,
        /**
         * Проведена
         */
        Сonducted,
        /**
         * Отменена
         */
        Cancelled,
    }
}

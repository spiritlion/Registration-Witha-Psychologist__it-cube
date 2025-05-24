package com.example.registrationwithapsychologist__itcube___newversion

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {
    private val booleanKey = booleanPreferencesKey("boolean_key")
    private val intKey = intPreferencesKey("int_key")

    // Функция для получения значения из DataStore
    val booleanFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[booleanKey] == true // Возвращает false по умолчанию
        }
    val intFlow : Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[intKey] ?: 0
        }

    // Функция для сохранения значения в DataStore
    suspend fun saveBoolean(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanKey] = value
        }
    }

    suspend fun saveInt(value: Int) {
        dataStore.edit { preferences ->
            preferences[intKey] = value
        }
    }
}
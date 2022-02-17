package com.a.hub.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.a.hub.helper.SimpleSecure
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppData @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val simpleSecure: SimpleSecure
) {

    private val ext = "_secured"

    fun getData(key: String) = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            preferences[stringPreferencesKey(key)].orEmpty()
        }

    suspend fun setData(key: String, value: String) {
        dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    //TODO encrypt search list
    fun getSecuredData(key: String) = dataStore.data
        .catch { emit(emptyPreferences()) }
        .secureMap<String> { preferences -> preferences[stringPreferencesKey(key + ext)].orEmpty() }

    suspend fun setSecuredData(key: String, value: String) {
        dataStore.secureEdit(value) { prefs, encryptedValue ->
            prefs[stringPreferencesKey(key + ext)] = encryptedValue
        }
    }

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }


    //TODO test it
    private inline fun <reified T> Flow<Preferences>.secureMap(
        crossinline getValue: (value: Preferences) -> String
    ): Flow<T> {
        return map {
            val decryptedValue = simpleSecure.decryptData(
                SimpleSecure.securityKeyAlias,
                getValue(it).split(SimpleSecure.bytesToStringSeparator).map { it.toByte() }.toByteArray()
            )
            Gson().fromJson(decryptedValue, object: TypeToken<T>() {}.type)
        }
    }

    //TODO test it
    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline store: (MutablePreferences, String) -> Unit
    ) {
        edit {
            val encryptedValue = simpleSecure.encryptData(
                SimpleSecure.securityKeyAlias,
                Gson().toJson(value)
            )
            store.invoke(it, encryptedValue.joinToString(SimpleSecure.bytesToStringSeparator))
        }
    }
}
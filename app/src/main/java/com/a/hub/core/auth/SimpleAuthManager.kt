package com.a.hub.core.auth

import android.content.Context
import android.content.SharedPreferences
import com.a.hub.helper.SimpleSecure
import com.google.gson.Gson
import javax.inject.Inject

class SimpleAuthManager @Inject constructor(
    context: Context,
    private val simpleSecure: SimpleSecure
) {

    var sharedPreferences: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun getAccessToken(): String? {
        val access: Access? = getAccess()
        if(access == null || access.token.isEmpty()) return null
        return access.token
    }


    private fun getAccess(): Access? {
        val string: String? = sharedPreferences.getString("access", null)
        if(string != null && string.isNotEmpty() && string.isNotBlank()){
            try {
                val decryptedValue = simpleSecure.decryptData(
                    SimpleSecure.securityKeyAlias,
                    string.split(SimpleSecure.bytesToStringSeparator).map {
                        it.toByte()
                    }.toByteArray()
                )
                return Gson().fromJson(decryptedValue, Access::class.java)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        return null
    }

    fun setAccess(access: Access): Boolean {
        if(!isAccessTokenOk(access.token)) return false

        val encrypted = simpleSecure.encryptData(
            SimpleSecure.securityKeyAlias,
            Gson().toJson(access)
        )
        val encryptedValue = encrypted.joinToString(SimpleSecure.bytesToStringSeparator)
        sharedPreferences.edit().putString("access", encryptedValue).apply()
        sharedPreferences.edit().putBoolean("session", true).apply()
        return true
    }

    fun clearAccess() {
        sharedPreferences.edit().putString("access", null).apply()
        sharedPreferences.edit().putBoolean("session", false).apply()
    }

    fun hasActiveSession(): Boolean {
        val access: Access? = getAccess()
        return isAccessTokenOk(access?.token)
    }

    private fun isAccessTokenOk(accessToken: String?): Boolean {
        return accessToken != null && accessToken.isNotEmpty()
    }
}
package com.a.hub.helper

import android.util.Base64

class Nat {

    private external fun getClientId(): String?
    private external fun getClientSecret(): String?
    private external fun startActivity()

    fun getId(): String {
        return String(Base64.decode(getClientId(), Base64.DEFAULT))
    }

    fun getSecret(): String {
        return String(Base64.decode(getClientSecret(), Base64.DEFAULT))
    }

}
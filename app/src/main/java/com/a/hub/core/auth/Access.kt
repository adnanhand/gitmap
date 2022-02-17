package com.a.hub.core.auth

import com.google.gson.annotations.SerializedName

data class Access(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("token_type")
    val type: String,
    val scope: String
)
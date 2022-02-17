package com.a.hub.data.model

import com.google.gson.annotations.SerializedName

abstract class Model() {
    var id: Long = 0
    var name: String = ""
    @SerializedName("created_at")
    var createdAt: String = ""
    @SerializedName("updated_at")
    var updatedAt: String = ""
}
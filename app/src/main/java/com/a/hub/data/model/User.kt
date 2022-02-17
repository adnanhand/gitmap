package com.a.hub.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    val type: String,
    val email: String,
    val company: String,
    val location: String,
    val blog: String,
    val bio: String,
    @SerializedName("twitter_username")
    val twitterUsername: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val url: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("organizations_url")
    val organizationsUrl: String,
    val followers: Int,
    val following: Int,
): Model()
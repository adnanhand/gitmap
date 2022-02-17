package com.a.hub.data

object Constants {

    object Api {
        const val URL = "https://api.github.com/"
        const val ACCEPT = "application/vnd.github.v3+json"
    }

    object Auth {
        const val URL = "https://github.com/login/oauth/access_token"
        const val REDIRECT_URI = "gitmap://callback"
        const val SCOPES = "repo,notifications,user:email"
    }

    const val URL = "url"
    const val PATH = "path"

}
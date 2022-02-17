package com.a.hub.data.api.interceptor

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpErrorInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        if (response.code != 200) {
            val code = response.code
            val message = response.message
            //val body = Gson().toJson(response.body.toString())
            Log.i("InterceptorResponse", "$code  =  $message")
        }
        return response
    }
}
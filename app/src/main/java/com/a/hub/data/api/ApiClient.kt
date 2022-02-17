package com.a.hub.data.api

import com.a.hub.data.Constants
import com.a.hub.data.api.interceptor.HttpErrorInterceptor
import com.a.hub.data.api.interceptor.HttpHeaderInterceptor
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        const val DEFAULT_TIMEOUT: Long = 10000
        const val CASH_SIZE = (5 * 1024 * 1024).toLong() // 10 MB
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.Api.URL)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                GsonConverterFactory.create(
                /*GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()*/
            ))
            .client(getHttpClient())
            .build()
    }

    private fun getHttpClient(): OkHttpClient {

        val ua = System.getProperty("http.agent") ?: "okhttp3-" + OkHttp.VERSION

        return OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            //.cache(Cache(context.cacheDir, CASH_SIZE))
            //.addInterceptor(HttpOfflineInterceptor())
            //.addNetworkInterceptor(HttpOnlineInterceptor())
            .addInterceptor(HttpErrorInterceptor())
            .addInterceptor(
                HttpHeaderInterceptor(mapOf(
                    "User-Agent" to ua,
                    "Accept" to Constants.Api.ACCEPT
                    //"Authorization" to token // TODO move to retrofit
                ))
            )
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    val service: ApiService = getRetrofit().create(ApiService::class.java)

}
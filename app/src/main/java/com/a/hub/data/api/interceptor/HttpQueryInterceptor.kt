package com.a.hub.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class HttpQueryInterceptor(
    private val queries: Map<String, String>
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlBuilder = chain
            .request()
            .url
            .newBuilder()

        for (query in queries) {
            urlBuilder.addQueryParameter(query.key, query.value)
        }
        //Timber.i(urlBuilder.build().toString())
        return chain.proceed(chain.request().newBuilder().url(urlBuilder.build()).build())
    }
}
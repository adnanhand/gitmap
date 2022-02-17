package com.a.hub.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HttpHeaderInterceptor(
    private val queries: Map<String, String>
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlBuilder = chain
            .request()
            .newBuilder()
            .header("Accept", "application/json")

        for (query in queries) urlBuilder.header(query.key, query.value)
        return chain.proceed(chain.request().newBuilder().build())
    }
}
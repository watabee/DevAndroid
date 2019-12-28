package com.github.watabee.devtoapp.data.api.interceptor

import com.github.watabee.devtoapp.data.api.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class RakutenApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val newRequest = if (request.method.equals("GET", ignoreCase = true)) {
            val newUrl = request.url.newBuilder()
                .addQueryParameter("applicationId", BuildConfig.RAKUTEN_APP_ID)
                .addQueryParameter("format", "json")
                .addQueryParameter("formatVersion", "2")
                .build()

            request.newBuilder().url(newUrl).build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}

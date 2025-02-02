package com.example.cinemania.core.network.interceptor

import com.example.core.network.utils.UrlHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ParameterInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val url = request.url.newBuilder().addQueryParameter("api_key", UrlHelper.API_KEY).build()
        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}
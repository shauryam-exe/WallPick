package com.code.wallpick.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    private const val BASE_URL = "https://api.pexels.com/v1/"

    private fun getAuthorizationInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()

        val new = originalRequest.newBuilder()
            .addHeader("Authorization:","563492ad6f91700001000001c97f1bf249ea491ebb47261cff9699f6")
            .build()

        chain.proceed(new)
    }

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
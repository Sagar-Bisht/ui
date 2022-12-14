package com.example.monoplug

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{

        private val AUTH = "Basic" + Base64.encodeToString("aaa:1".toByteArray(),Base64.NO_WRAP)
        private const val BASE_URL ="http://192.168.3.174:8000/api/v1/"

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                var requestBuilder = original.newBuilder().addHeader("Authorization", AUTH)
                    .method(original.method, original.body)
                var request = requestBuilder.build()
                chain.proceed(request)
            }.build()

        val instance :RetroServiceInterface by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            retrofit.create(RetroServiceInterface::class.java)
        }
    }


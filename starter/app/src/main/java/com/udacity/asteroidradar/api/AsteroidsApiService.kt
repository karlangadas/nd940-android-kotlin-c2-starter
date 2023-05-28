package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.io.IOException

// extracted from: https://stackoverflow.com/questions/71081146/retrofit-get-query-with-api-key
private class ApiKeyInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url()
        val url =
            originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
        request.url(url)
        return chain.proceed(request.build())
    }
}

// Add the interceptor to OkHttpClient
private val apiKeyBuilder = OkHttpClient.Builder()
    .addInterceptor(ApiKeyInterceptor())

// Creating a client out of the builder
private val apiKeyClient = apiKeyBuilder.build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .client(apiKeyClient)
    .build()

interface AsteroidsApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getPropertiesAsync(): String
}

object AsteroidsApi {
    val retrofitService: AsteroidsApiService by lazy {
        retrofit.create(AsteroidsApiService::class.java)
    }
}
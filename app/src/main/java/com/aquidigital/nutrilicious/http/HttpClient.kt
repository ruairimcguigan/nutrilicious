package com.aquidigital.nutrilicious.http

import com.aquidigital.nutrilicious.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  usdaApi object is the only declaration in HttpClient.kt that is exposed to the outside
 */
val USDA_USDA_API: UsdaApi by lazy { usdaClient.create(UsdaApi::class.java) }

val BASE_URL: String
    get() = "https://api.nal.usda.gov/ndb/"

private val usdaClient by lazy { buildClient() }


private fun buildClient(): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(buildHttpClient())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

private fun buildHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(loggingInterceptor())
    .addInterceptor(apiKeyInterceptor())
    .build()

private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
        Level.BODY // only logs in debug
    } else {
        Level.NONE //
    }
}

private fun apiKeyInterceptor() = injectQueryParams("api_key" to BuildConfig.API_KEY)



/**
 * Because Interceptor is a SAM interface coming from Java, you can use Kotlin’s SAM conversions
 * to create an interceptor with lambda syntax. This implicitly overrides Interceptor.intercept
 * to intercept the request chain and add query parameters to it. The details of this method are
 * OkHttp-specific, but notice how apply can be extremely useful in combination with builder-style
 * methods.
 */
private fun injectQueryParams(vararg params: Pair<String, String>): Interceptor = Interceptor { chain ->

    val originalRequest = chain.request()

    val urlWithParams = originalRequest.url().newBuilder()
        .apply {
            params.forEach {
                addQueryParameter(it.first, it.second)
            }
        }.build()

    val newRequest = originalRequest.newBuilder().url(urlWithParams).build()

    chain.proceed(newRequest)
}

package com.aquidigital.nutrilicious.http

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsdaApi {

    @GET("search?format=json")
    fun getFoods(
        @Query("q") searchTerm: String,
        @Query("sort") sortBy: Char = 'r',
        @Query("ds") dataSource: String = "Standard Reference",
        @Query("offset") offset: Int = 0
    ) : Call<ResponseBody>  // allows raw json to be retrived
}
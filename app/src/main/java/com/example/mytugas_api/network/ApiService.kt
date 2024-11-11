package com.example.mytugas_api.network

import com.example.mytugas_api.model.Dogs
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("breeds/image/random")
    fun getAllDogs(): Call<Dogs>
}
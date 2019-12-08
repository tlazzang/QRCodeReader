package com.example.idus_homework.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofitInstance = Retrofit.Builder()
        .baseUrl(" https://eq0lwb7e8e.execute-api.ap-northeast-2.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
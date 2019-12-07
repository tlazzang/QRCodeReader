package com.example.idus_homework.Network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {
    @Headers("Content-Type: application/json")
    @POST("default/android-dev-recruit")
    fun postText(@Body body: HashMap<String, Any>): Call<ResponseBody>
}
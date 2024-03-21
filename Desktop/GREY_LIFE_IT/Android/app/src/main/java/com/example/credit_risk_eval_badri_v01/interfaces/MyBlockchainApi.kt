package com.example.credit_risk_eval_badri_v01.interfaces

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyBlockchainApi {
    data class HelloWorldResponse(val output: Array<String>)
//    data class RequestData(val newString: Array<String>)
    data class RequestData(@SerializedName("_array") val stringArray: List<String>)
    data class ResponseData(val newString: String)

    @POST("setStringArray")
    fun PostData(
        @Query("kld-from") kldFrom: String,
        @Body requestData: RequestData
    ): Call<ResponseData>

    @GET("getStringArray")
    fun getData(@Query("kld-from") kldFrom: String): Call<HelloWorldResponse>
}
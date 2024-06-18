package com.example.runaway

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//https://apis.data.go.kr/1741000/TsunamiShelter4/getTsunamiShelter4List?ServiceKey=nfpiyRzbLt60%2BMjNWKX0L0etodMnmVPkRC5w8%2FIaHOUS3WSW0ZeBn3jow4IQRqHyvON6f2FXBB%2BLxfa8G8HQ7Q%3D%3D&pageNo=1&numOfRows=5&type=json
interface NetworkService {
    @GET("getTsunamiShelter4List")
    fun getJsonList(
        @Query("ServiceKey") servivekey:String,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("type") type:String
    ): Call<JsonResponse>
}
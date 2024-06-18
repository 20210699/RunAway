package com.example.runaway

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection {
    companion object{ // 전역 변수
        private const val BASE_URL = "https://apis.data.go.kr/1741000/TsunamiShelter4/"

        var jsonNetServ : NetworkService
        val jsonRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // 전달받은 response : string -> json
                .build()

        val xmlNetServ : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))// 전달받은 response : string -> xml
                .build()

        init{
            jsonNetServ = jsonRetrofit.create(NetworkService::class.java)
            xmlNetServ = xmlRetrofit.create(NetworkService::class.java)
        }
    }
}
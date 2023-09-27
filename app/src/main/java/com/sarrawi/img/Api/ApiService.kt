package com.sarrawi.img.Api

import com.sarrawi.img.model.ImgsRespone
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface ApiService {

    @GET("imgsapi/{ID_Type_id}")
    suspend fun getImgs_Ser
                (@Path("ID_Type_id") ID_Type_id:Int
    ):Response<ImgsRespone>

    @GET("/imgtypes_api/")
    suspend fun getImgs_Types()

    companion object{

        var retrofitService:ApiService?=null
        fun provideRetrofitInstance(): ApiService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.sarrawi.bio/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!
        }

    }
}
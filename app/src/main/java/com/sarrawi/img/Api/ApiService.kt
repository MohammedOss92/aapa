package com.sarrawi.img.Api

import com.sarrawi.img.model.ImgsRespone
import com.sarrawi.img.model.MyImgsRespone2
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query


interface ApiService {



    @GET("imgsapi/{ID_Type_id}")
    suspend fun getImgs_Ser
                (@Path("ID_Type_id") ID_Type_id:Int
    ):Response<ImgsRespone>

    @GET("imgsapipa/{ID_Type_id}")
    suspend fun getImgsData(
        @Path("ID_Type_id") id: Int,
        @Query("page") page: Int): Response<ImgsRespone>

    @GET("snippetsids/{ID_Type_id}") // تغيير نهاية الطريق الخاصة بك
    suspend fun getsnippetsid(
        @Path("ID_Type_id") ID_Type_id: Int,
        @Query("page") page: Int
    ): Response<MyImgsRespone2>

    @GET("imgsapi/{ID_Type_id}")
    fun getImgs_Serr(
        @Path("ID_Type_id") ID_Type_id: Int,
        @Query("page") page: Int
    ): Response<ImgsRespone>





    @GET("/imgtypes_api/")
    suspend fun getImgs_Types()

    companion object{

        var retrofitService: ApiService? = null

        fun provideRetrofitInstance(): ApiService {
            if (retrofitService == null) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.sarrawi.bio/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient) // قم بتعيين العميل الذي يحتوي على Interceptor
                    .build()

                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!
        }

    }
}
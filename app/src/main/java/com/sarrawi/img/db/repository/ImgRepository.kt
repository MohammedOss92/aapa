package com.sarrawi.img.db.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.model.ImgsRespone
import retrofit2.Response


class ImgRepository(val apiService: ApiService,app:Application) {




    suspend fun getImgs_Repo(ID_Type_id: Int) = apiService.getImgs_Ser(ID_Type_id)

    suspend fun getImgs_Repoa(ID_Type_id: Int, startIndex: Int, itemsPerPage: Int) = apiService.getImgs_Sera(ID_Type_id, startIndex, itemsPerPage)


    suspend fun getImgsData(ID_Type_id: Int, page: Int) = apiService.getImgsData(ID_Type_id, page)





}
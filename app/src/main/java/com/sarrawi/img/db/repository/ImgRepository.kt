package com.sarrawi.img.db.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.model.ImgsRespone
import com.sarrawi.img.paging.ImgPaging
import retrofit2.Response


class ImgRepository(val apiService: ApiService,app:Application) {




    suspend fun getImgs_Repo(ID_Type_id: Int) = apiService.getImgs_Ser(ID_Type_id)




//    suspend fun getImgsData(ID_Type_id: Int, page: Int) = apiService.getImgsData(ID_Type_id, page)
    suspend fun getImgsData(ID_Type_id: Int): LiveData<PagingData<ImgsModel>> {
   val response =  Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            ImgPaging(
                apiService, ID_Type_id
            )
        },
    ).liveData
    return response
}





}
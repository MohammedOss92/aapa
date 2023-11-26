package com.sarrawi.img.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.paging.ImgPaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ImgRepository(val apiService: ApiService,app:Application) {




    suspend fun getImgs_Repo(ID_Type_id: Int) = apiService.getImgs_Ser(ID_Type_id)





//    suspend fun getSnippets(ID_Type_id: Int): ImgsRespone2? {
//        return apiService.getSnipp(ID_Type_id)
//    }


//    suspend fun getSnippetsids(ID_Type_id: Int):LiveData<PagingData<ImgsModel>>{
//        val response =  Pager(
//            config = PagingConfig(
//                pageSize = 12,
//                enablePlaceholders = false,
//
//                ),
//            pagingSourceFactory = {
//                ImgPaging(
//                    apiService, ID_Type_id
//                )
//            },
//        ).liveData
//        return response
//    }

    suspend fun getSnippetsidss(ID_Type_id: Int): LiveData<PagingData<ImgsModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImgPaging(apiService, ID_Type_id) }
        ).liveData
    }

    suspend fun getSnippetsids(ID_Type_id: Int): LiveData<PagingData<ImgsModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImgPaging(apiService, ID_Type_id) }
        ).liveData
    }



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



    fun fetchImagesPaged(id: Int, startPage: Int): Flow<List<ImgsModel>> = flow {
        var currentPage = startPage

        while (true) {
            val response = apiService.getImgs_Serr(id, currentPage)

            if (response.isSuccessful) {
                val images = response.body()?.results ?: emptyList()
                emit(images)

                if (images.size < currentPage) {
                    // لا توجد المزيد من الصور، يمكنك إيقاف الحلقة هنا
                    break
                }

                currentPage++
            } else {
                // معالجة الخطأ هنا
                break
            }
        }
    }






}
package com.sarrawi.img.paging

import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.model.ImgsRespone

class ImgPaging(private val apiService: ApiService, var ID_Type_id: Int) : PagingSource<Int, ImgsModel>() {

    private val TAG: String = "TAG_PAGING"

    private var lastLoadedPage: Int? = null

    override fun getRefreshKey(state: PagingState<Int, ImgsModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, ImgsModel> {
        try {
            val currentPage = params.key ?: 1

            // تحقق مما إذا كانت هذه الصفحة هي نفس الصفحة التي تم تحميلها آخر مرة
            if (currentPage == lastLoadedPage) {
                // قم بإرجاع نتيجة فارغة للإشارة إلى عدم وجود بيانات جديدة
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
            }

            val response = apiService.getImgsData(ID_Type_id, currentPage)
            val responseData = mutableListOf<ImgsModel>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            // قم بتحديث الصفحة التي تم تحميلها آخر مرة
//            lastLoadedPage = currentPage

            // قم بحساب المفتاح التالي بناءً على ما إذا كانت هناك بيانات في الاستجابة
            val nextKey = if (data.isNotEmpty()) currentPage + 1 else null

            return LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    fun updateID_TypeId(newID: Int) {
        ID_Type_id = newID
    }
}

//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImgsModel> {
//        try {
//            val currentPage = params.key ?: 1
//
//            // Check if this page is the same as the last loaded page
//            if (currentPage == lastLoadedPage) {
//                // Return an empty result to indicate no new data
//                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
//            }
//
//            val response = apiService.getImgsData(ID_Type_id, currentPage)
//            val responseData = mutableListOf<ImgsModel>()
//            val data = response.body()?.results ?: emptyList()
//            responseData.addAll(data)
//
//            // Update the last loaded page
//            lastLoadedPage = currentPage
//
//            return LoadResult.Page(
//                data = responseData,
//                prevKey = if (currentPage == 1) null else currentPage - 1,
//                nextKey = currentPage + 1
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }




//    override fun getRefreshKey(state: PagingState<Int, ImgsModel>): Int? {
//        val anchorPosition = state.anchorPosition ?: return null
//        val closestPage = state.closestPageToPosition(anchorPosition)
//
//        // Check if the user is scrolling to the top
//        if (closestPage?.prevKey != null && state.pages.size > 1 && state.pages[1].prevKey != null) {
//            return null
//        }
//
//        // If scrolling down or initial load, use current page key
//        return state.pages.firstOrNull()?.prevKey
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImgsModel> {
//        try {
//            val page = params.key ?: 1
//            // استخدام ID_Type_id كمعامل
//            val response = apiService.getImgsData(ID_Type_id, page)
//            if (response.isSuccessful) {
//                val data = response.body()?.results ?: emptyList()
//                val prevKey = if (page == 0) null  else page + 1
//                val nextKey = if (response.body()?.currentPage == response.body()?.totalPages) null else page + 1
//
//                return LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
//            } else {
//                // إذا كان هناك خطأ في الاستدعاء
//                return LoadResult.Error(Exception("Error loading data"))
//            }
//        } catch (e: Exception) {
//            // إذا حدث استثناء أثناء الاستدعاء
//            return LoadResult.Error(e)
//        }
//    }



//    override suspend fun load(params: LoadParams<Int>):
//            LoadResult<Int, ImgsModel> {
//
//        return try {
//            val currentPage = params.key ?: 1
//            val response = apiService.getImgsData(ID_Type_id, currentPage)
//            val responseData = mutableListOf<ImgsModel>()
//            val data = response.body()?.results ?: emptyList()
//            responseData.addAll(data)
//
//            LoadResult.Page(
//                data = responseData,
//                prevKey = if (currentPage == 1) null else -1,
//                nextKey = currentPage.plus(1)
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//
//    }




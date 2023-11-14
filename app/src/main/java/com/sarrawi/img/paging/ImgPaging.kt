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
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImgsModel> {
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
            lastLoadedPage = currentPage
            // قم بحساب المفتاح التالي بناءً على ما إذا كانت هناك بيانات في الاستجابة
            val nextKey = if (data.isNotEmpty()) currentPage + 1 else null
            // إذا كانت هذه آخر صفحة و nextKey هو null، فلا تقوم بإرجاع نتيجة
            if (currentPage == response.body()?.totalPages && nextKey == null) {
                Log.d(TAG, "Last page, nextKey: $nextKey")
                Log.d("Test", "Hello World")
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
                Log.d("Test", "Hello World")
                Log.d(TAG, "Last page, nextKey: $nextKey")
            }
            return LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}




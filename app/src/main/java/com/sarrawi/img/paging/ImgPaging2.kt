package com.sarrawi.img.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.model.results

class ImgPaging2(private val apiService: ApiService, var ID_Type_id: Int) : PagingSource<Int, results>() {

    private val TAG:String = "TAG_PAGING"
    override fun getRefreshKey(state: PagingState<Int, results>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)?:state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, results> {
//        try {
//            val page = params.key ?: 1
//
//            // استخدام ID_Type_id كمعامل
//            val response = apiService.getsnippets(ID_Type_id,"json" ,page)
//
//            if (response.isSuccessful) {
//                val data = response.body()?.results ?: emptyList()
//                val prevKey = if (page == 0) null else page - 1
//
//
//                //val nextKey = if (data.isEmpty()) null else page + 1
//                Log.d(TAG, "load: currentPage is ${response.body()?.current_page} and total is ${response.body()?.total_pages}")
//                val nextKey = if(response.body()?.current_page == response.body()?.total_pages) null else page+1
//
//
//                Log.d(TAG, "load: nextKey is $nextKey and prevKeyis $prevKey anf page = $page id = $ID_Type_id")
//                // إعادة البيانات ومفاتيح الصفحات
//                Log.d(TAG,"${response.body()?.results?.size}")
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

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, results> {
        return try {
            val page = params.key ?: 1

            // استخدام ID_Type_id كمعامل
            val response = apiService.getsnippets(ID_Type_id,  page)

            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()
                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (response.body()?.current_page == response.body()?.total_pages) null else page + 1

                LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
            } else {
                // إذا كان هناك خطأ في الاستدعاء
                LoadResult.Error(Exception("Error loading data"))
            }
        } catch (e: Exception) {
            // إذا حدث استثناء أثناء الاستدعاء
            LoadResult.Error(e)
        }
    }


    fun updateID_TypeId(newID: Int) {
        ID_Type_id = newID
    }
}

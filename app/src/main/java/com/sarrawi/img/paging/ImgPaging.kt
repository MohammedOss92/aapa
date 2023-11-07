package com.sarrawi.img.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.model.ImgsRespone

class ImgPaging(private val apiService: ApiService,var ID_Type_id: Int) : PagingSource<Int, ImgsModel>() {

    private val TAG:String = "TAG_PAGING"
    override fun getRefreshKey(state: PagingState<Int, ImgsModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)?:state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImgsModel> {
        try {
            val page = params.key ?: 1

            // استخدام ID_Type_id كمعامل
            val response = apiService.getImgsData(ID_Type_id, page)

            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()
                val prevKey = if (page == 0) null else page - 1


                //val nextKey = if (data.isEmpty()) null else page + 1
                Log.d(TAG, "load: currentPage is ${response.body()?.currentPage} and total is ${response.body()?.totalPages}")
                val nextKey = if(response.body()?.currentPage == response.body()?.totalPages) null else page+1

                Log.d(TAG, "load: nextKey is $nextKey and prevKeyis $prevKey anf page = $page id = $ID_Type_id")
                // إعادة البيانات ومفاتيح الصفحات
                Log.d(TAG,"${response.body()?.results?.size}")
                return LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
            } else {
                // إذا كان هناك خطأ في الاستدعاء
                return LoadResult.Error(Exception("Error loading data"))
            }
        } catch (e: Exception) {
            // إذا حدث استثناء أثناء الاستدعاء
            return LoadResult.Error(e)
        }
    }

    fun updateID_TypeId(newID: Int) {
        ID_Type_id = newID
    }
}

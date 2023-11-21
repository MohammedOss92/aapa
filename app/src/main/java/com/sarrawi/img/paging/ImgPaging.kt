package com.sarrawi.img.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.model.ImgsModel

class ImgPaging(private val apiService: ApiService, private val ID_Type_id: Int) :
    PagingSource<Int, ImgsModel>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    private var isLoading = false

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImgsModel> {
        if (isLoading) {
            // تجنب إعادة استدعاء load عندما تكون العملية قيد التحميل
            return LoadResult.Error(Exception("Loading is already in progress"))
        }

        try {
            isLoading = true

            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val pageSize = params.loadSize

            Log.d("ImgPaging", "Loading page $currentPage with pageSize $pageSize")

            val response = apiService.getsnippetsid(ID_Type_id, currentPage)
            Log.i("hahahahahaha", "load: ${response.body()}")
            if (response.isSuccessful) {
                val data = response.body()?.results?.ImgsModel ?: emptyList()

                Log.d("dImgPaging", "Loaded data: $data")

                return LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                    nextKey = if (data.isEmpty()) null else currentPage + 1
                )
            } else {
                Log.e("ImgPaging", "Error loading data. Response: ${response.code()}, ${response.message()}")
                return LoadResult.Error(Exception("Error loading data. Response: ${response.code()}, ${response.message()}"))
            }

        } catch (e: Exception) {
            Log.e("ImgPaging", "Exception during data loading: $e")
            Log.e("ImgPaging", "Error loading data. Exception: ${e.message}")

            return LoadResult.Error(e)
        } finally {
            isLoading = false
        }
    }

    // الجزء الباقي من الكود...



    override fun getRefreshKey(state: PagingState<Int, ImgsModel>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }
    }





//override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImgsModel> {
//    try {
//        val currentPage = params.key ?: STARTING_PAGE_INDEX
//        val pageSize = params.loadSize
//
//        Log.d("ImgPaging", "Loading page $currentPage with pageSize $pageSize")
//
//        val response = apiService.getsnippetsid(ID_Type_id, currentPage)
//        if (response.isSuccessful) {
//            val myResponse = response.body()
//            val data = myResponse?.results?.map { myImgModel ->
//                // هنا قم بتحويل MyImgsModel إلى ImgsModel إذا كان ذلك ضروريًا
//                // يمكنك تركه كما هو إذا كان نموذج البيانات متطابقًا
//                ImgsModel(
//                    id = myImgModel.id,
//                    ID_Type_id = myImgModel.ID_Type_id,
//                    new_img = myImgModel.new_img,
//                    image_url = myImgModel.image_url
//                    // ... قم بإضافة المزيد من الحقول إذا كانت مطلوبة
//                )
//            } ?: emptyList()
//
//            Log.d("dImgPaging", "Loaded data: $data")
//
//            return LoadResult.Page(
//                data = data,
//                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
//                nextKey = if (data.isEmpty()) null else currentPage + 1
//            )
//        } else {
//            Log.e(
//                "ImgPaging",
//                "Error loading data. Response: ${response.code()}, ${response.message()}"
//            )
//            return LoadResult.Error(
//                Exception(
//                    "Error loading data. Response: ${response.code()}, ${response.message()}"
//                )
//            )
//        }
//    } catch (e: Exception) {
//        Log.e("ImgPaging", "Exception during data loading: $e")
//        Log.e("ImgPaging", "Error loading data. Exception: ${e.message}")
//
//        return LoadResult.Error(e)
//    }
//}
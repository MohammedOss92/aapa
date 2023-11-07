package com.sarrawi.img.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sarrawi.img.db.Dao.FavoriteImageDao
import com.sarrawi.img.model.FavoriteImage


class ImgPagingFav(val favoriteImageDao: FavoriteImageDao): PagingSource<Int, FavoriteImage>() {

    override fun getRefreshKey(state: PagingState<Int, FavoriteImage>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)?:state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteImage> {
        try {
            val currentPage = params.key ?: 1
            val response = favoriteImageDao.getAllFavoriteImages()

            return LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
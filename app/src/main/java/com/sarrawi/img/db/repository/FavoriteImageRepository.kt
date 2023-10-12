package com.sarrawi.img.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sarrawi.img.db.Dao.FavoriteImageDao
import com.sarrawi.img.db.Dao.Imgs_Dao
import com.sarrawi.img.db.Pic_Databases
import com.sarrawi.img.model.FavoriteImage

class FavoriteImageRepository(app:Application) {
    val favoriteImageDao: FavoriteImageDao
    init {
        val database = Pic_Databases.getInstance(app)

        favoriteImageDao = database.getFavoriteImageDao()
    }
    suspend fun addFavoriteImage(favoriteImage: FavoriteImage) {
        favoriteImageDao.insertFavoriteImage(favoriteImage)
    }

    suspend fun removeFavoriteImage(favoriteImage: FavoriteImage) {
        favoriteImageDao.deleteFavoriteImage(favoriteImage)
    }

    suspend fun getAllFavoriteImages(): List<FavoriteImage> {
        return favoriteImageDao.getAllFavoriteImages()
    }

    fun getAllFavorite(): LiveData<List<FavoriteImage>> {
        return favoriteImageDao.getAllFavorite()
    }
}

package com.sarrawi.img.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sarrawi.img.db.Dao.Favorite_Dao
import com.sarrawi.img.db.Dao.ImgType_Dao
import com.sarrawi.img.db.Pic_Databases
import com.sarrawi.img.model.FavoriteModel
import com.sarrawi.img.model.Img_Types_model

class Fav_Repo(app: Application) {

    private var favoriteDao: Favorite_Dao
    private var allCategory: LiveData<List<FavoriteModel>>

    init {
        val database = Pic_Databases.getInstance(app)
        favoriteDao = database.getFavoriteDao()
        allCategory = favoriteDao.getAllFav()
    }

    suspend fun add_fav_repo(fav: FavoriteModel) {

        favoriteDao.add_fav_Dao(fav)
    }

    suspend fun getAllFav() = favoriteDao.getAllFav()

    // delete favorite item from db
    suspend fun deleteFav(fav: FavoriteModel) {

        favoriteDao.deletefav_Dao(fav)
    }
}
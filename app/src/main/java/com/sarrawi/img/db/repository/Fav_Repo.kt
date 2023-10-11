package com.sarrawi.img.db.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.sarrawi.img.db.Dao.Favorite_Dao
import com.sarrawi.img.db.Dao.ImgType_Dao
import com.sarrawi.img.db.Pic_Databases
import com.sarrawi.img.model.FavoriteModel
import com.sarrawi.img.model.Img_Types_model

class Fav_Repo(app: Application) {

    private var favoriteDao: Favorite_Dao

    init {
        val database = Pic_Databases.getInstance(app)
        favoriteDao = database.getFavoriteDao()

    }

    suspend fun add_fav_repo(fav: FavoriteModel) {

        favoriteDao.add_fav_Dao(fav)
    }

    suspend fun getAllFav(): LiveData<List<FavoriteModel>>{
        Log.e("tessst","entred666")
        return favoriteDao?.getAllFav()!!
    }

    // delete favorite item from db
    suspend fun deleteFav(fav: FavoriteModel) {

        favoriteDao?.deletefav_Dao(fav)
    }

    fun getFavoriteByID(id: Int): FavoriteModel? {
        return favoriteDao?.getFavoriteByID(id)
    }


}
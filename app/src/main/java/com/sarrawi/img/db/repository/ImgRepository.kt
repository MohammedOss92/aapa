package com.sarrawi.img.db.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.db.Dao.Favorite_Dao
import com.sarrawi.img.db.Dao.Imgs_Dao
import com.sarrawi.img.db.Pic_Databases
import com.sarrawi.img.model.FavoriteModel
import com.sarrawi.img.model.ImgsModel

class ImgRepository(val apiService: ApiService,app:Application) {
    val imgsDao:Imgs_Dao
    val favoriteDao:Favorite_Dao

    init {
        val database = Pic_Databases.getInstance(app)
        imgsDao = database.getImgsDao()
        favoriteDao = database.getFavoriteDao()
    }


    suspend fun getImgs_Repo(ID_Type_id: Int) = apiService.getImgs_Ser(ID_Type_id)


//    suspend fun insert_imgs_repo(imgs: List<ImgsModel>?) {
//        if (!imgs.isNullOrEmpty()) {
//            imgsDao.insert_imgs(imgs)
//        }
//    }
//
//    suspend fun update_fav(id: Int,state:Boolean) {
//
//        imgsDao.update_fav(id,state)
//    }

    /***************************/
    suspend fun add_fav(fav: FavoriteModel) {

        favoriteDao.add_fav_Dao(fav)
    }

    suspend fun getAllFav(): LiveData<List<FavoriteModel>>{
        Log.e("tessst","entred666")
        return favoriteDao.getAllFav()
    }

    // delete favorite item from db
    suspend fun deleteFav(fav: FavoriteModel) {

        favoriteDao.deletefav_Dao(fav)
    }

}
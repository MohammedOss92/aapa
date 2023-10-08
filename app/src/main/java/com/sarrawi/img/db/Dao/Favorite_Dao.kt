package com.sarrawi.img.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sarrawi.img.model.FavoriteModel
import com.sarrawi.img.model.Img_Types_model

@Dao
interface Favorite_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add_fav_Dao(favoriteModel: FavoriteModel)

    @Query("Select * from Favorite_table")
//    @Query("select e.*, c.MsgTypes as typeTitle from  Favorite_table " +
//            "e left join msg_types_table c  on " +
//            " c.id = e.ID_Type_id where " +
//            "e.ID_Type_id order by c.id DESC")
    fun getAllFav(): LiveData<List<FavoriteModel>>

    @Delete
    suspend fun deletefav_Dao(item:FavoriteModel)


}
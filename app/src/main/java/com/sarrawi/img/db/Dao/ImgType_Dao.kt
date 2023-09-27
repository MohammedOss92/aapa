package com.sarrawi.img.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarrawi.img.model.Img_Types_model

@Dao
interface ImgType_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add_type_Dao(imgTypes: Img_Types_model)

    @Query("select * from Img_Types")
    fun get_all_type_Dao():LiveData<List<Img_Types_model>>
}
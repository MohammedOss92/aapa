package com.sarrawi.img.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sarrawi.img.db.Dao.ImgType_Dao
import com.sarrawi.img.db.Pic_Databases
import com.sarrawi.img.model.Img_Types_model

class ImgTypes_Repository(app:Application) {
    private var imgtypeDao: ImgType_Dao
    private var allCategory: LiveData<List<Img_Types_model>>

    init {
        val database = Pic_Databases.getInstance(app)
        imgtypeDao = database.getTypesDao()
        allCategory = imgtypeDao.get_all_type_Dao()
    }

    suspend fun insert_Types_repo(imgTypesModel: Img_Types_model) = imgtypeDao.add_type_Dao(imgTypesModel)

    fun getAllTypes():LiveData<List<Img_Types_model>> = imgtypeDao.get_all_type_Dao()



}
package com.sarrawi.img.db

import com.sarrawi.img.R
import com.sarrawi.img.db.Dao.ImgType_Dao
import com.sarrawi.img.model.Img_Types_model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun reTypes(database: Pic_Databases?) {
        database?.let { db ->
            withContext(Dispatchers.IO) {
                val imgtypeDao: ImgType_Dao = db.getTypesDao()

                imgtypeDao.add_type_Dao((Img_Types_model(1,"اسلامية", R.drawable.isl)))
                imgtypeDao.add_type_Dao((Img_Types_model(2,"الاب", R.drawable.fa1)))
                imgtypeDao.add_type_Dao((Img_Types_model(3,"الام", R.drawable.mo1)))
                imgtypeDao.add_type_Dao((Img_Types_model(4,"الجمعة", R.drawable.fri)))
                imgtypeDao.add_type_Dao((Img_Types_model(5,"حب", R.drawable.love)))
                imgtypeDao.add_type_Dao((Img_Types_model(6,"حكم و عبر", R.drawable.hek)))
                imgtypeDao.add_type_Dao((Img_Types_model(7,"خواطر", R.drawable.khawa)))
                imgtypeDao.add_type_Dao((Img_Types_model(8,"صباح و مساء", R.drawable.sabah)))
                imgtypeDao.add_type_Dao((Img_Types_model(9,"صداقة", R.drawable.frien)))
                imgtypeDao.add_type_Dao((Img_Types_model(10,"كومنتات", R.drawable.comm)))
                imgtypeDao.add_type_Dao((Img_Types_model(11,"متنوعة", R.drawable.mix)))
                imgtypeDao.add_type_Dao((Img_Types_model(12,"ورد", R.drawable.war)))
            }
        }
}

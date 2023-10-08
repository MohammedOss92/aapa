package com.sarrawi.img.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sarrawi.img.db.Dao.Favorite_Dao
import com.sarrawi.img.db.Dao.ImgType_Dao
import com.sarrawi.img.model.FavoriteModel
import com.sarrawi.img.model.Img_Types_model
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@Database(entities = [Img_Types_model::class,FavoriteModel::class], version = 3, exportSchema = false)
abstract class Pic_Databases: RoomDatabase() {

    abstract fun getTypesDao():ImgType_Dao
    abstract fun getFavoriteDao():Favorite_Dao

    companion object{

        @Volatile
        private var instance:Pic_Databases?=null

//        fun getInstance(con:Context):Pic_Databases{
//            if (instance==null){
//                instance = Room.databaseBuilder(con,Pic_Databases::class.java,"imgDatabase")
//
//                    .addCallback(object :Callback(){
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            Log.d("imgDatabase", "img with data...")
//                            GlobalScope.launch(Dispatchers.IO) { reTypes(instance) }
//                        }
//                    })
//                    .build()
//            }
//            return instance!!
    @Synchronized
    fun getInstance(ctx: Context): Pic_Databases {
        if(instance == null)
            instance = Room.databaseBuilder(ctx.applicationContext, Pic_Databases::class.java,
                "note_database")
                .fallbackToDestructiveMigration()
                .addCallback(roomCallback)
                .build()

        return instance!!

    }
        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch(Dispatchers.IO) { reTypes(instance) }
            }
        }

        }
    }




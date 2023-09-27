package com.sarrawi.img.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Img_Types")
data class Img_Types_model(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id:Int,
    @ColumnInfo("ImgTypes")
    var ImgTypes :String,
    @ColumnInfo("Pic")
    var Pic :Int


)

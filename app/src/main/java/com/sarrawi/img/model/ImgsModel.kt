package com.sarrawi.img.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ImgsModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    @SerializedName("id")
    var id:Int?=0,

    @ColumnInfo("ID_Type_id")
    @SerializedName("ID_Type_id")
    var ID_Type_id : Int,

    @ColumnInfo("new_img")
    @SerializedName("new_img")
    var new_img :Int,

    @ColumnInfo("image_url")
    @SerializedName("image_url")
    var image_url :String,


    var is_fav:Boolean = false


)


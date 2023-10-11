package com.sarrawi.img.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Favorite_table")
data class FavoriteModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int ?= null,

    @ColumnInfo("ID_Type_id", index = true)
    var ID_Type_id: Int,

    @ColumnInfo("new_imgs")
    var new_imgs: Int,

    @ColumnInfo("image_url")
    var image_url: String,






)
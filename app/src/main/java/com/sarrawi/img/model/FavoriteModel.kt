package com.sarrawi.img.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Favorite_table")
data class FavoriteModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @NonNull
    var id : Int = 0,

    @ColumnInfo("ID_Type_id", index = true)
    @SerializedName("ID_Type_id")
    var ID_Type_id : Int,

    @ColumnInfo("new_msgs")
    @SerializedName("new_msgs")
    var new_msgs : Int,

    @ColumnInfo("image_url")
    @SerializedName("image_url")
    var image_url :String,

    @ColumnInfo("TypeTitle")
    @SerializedName("TypeTitle")
    var TypeTitle : String,




)
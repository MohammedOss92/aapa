package com.sarrawi.img.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ImgsModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("ID_Type_id")
    val ID_Type: Int,

    @SerializedName("new_img")
    val new_img: Int,

    @SerializedName("image_url")
    var image_url: String,

    var is_fav:Boolean = false
)
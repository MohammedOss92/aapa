package com.sarrawi.img.model

import com.google.gson.annotations.SerializedName

data class ImgsModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("ID_Type_id")
    val ID_Type: Int,

    @SerializedName("new_img")
    val new_img: String,

    @SerializedName("image_url")
    var image_url: String,


    var is_fav:Boolean = false
)
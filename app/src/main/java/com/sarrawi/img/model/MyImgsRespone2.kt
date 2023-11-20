package com.sarrawi.img.model

import com.google.gson.annotations.SerializedName

data class MyImgsRespone2(

    @SerializedName("count")
    val count: Int,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("current_page")
    val current_page: Int,
    @SerializedName("ImgsModel")
    val results: List<ImgsModel>,

)

package com.sarrawi.img.model

import com.google.gson.annotations.SerializedName

data class MyImgsRespone(

    @SerializedName("total_pages")
    val total_pages:Int,
    @SerializedName("current_page")
    val current_page:Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<results>
)


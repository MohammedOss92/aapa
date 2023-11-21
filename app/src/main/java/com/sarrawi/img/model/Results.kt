package com.sarrawi.img.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("ImgsModel")
    val ImgsModel: List<ImgsModel>
)
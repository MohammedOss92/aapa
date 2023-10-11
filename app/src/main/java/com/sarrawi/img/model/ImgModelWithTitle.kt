package com.sarrawi.img.model

import androidx.room.Embedded


data class ImgModelWithTitle (

    @Embedded
    var imgsModel: ImgsModel? = null,
    val typeTitle: String = ""
    )
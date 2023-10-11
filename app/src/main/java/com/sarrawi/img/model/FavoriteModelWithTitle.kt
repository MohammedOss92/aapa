package com.sarrawi.img.model

import androidx.room.Embedded

data class FavoriteModelWithTitle(
    @Embedded
    var favoriteModel: FavoriteModel? = null,
    val typeTitle: String = ""
)

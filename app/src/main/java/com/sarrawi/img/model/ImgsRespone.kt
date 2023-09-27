package com.sarrawi.img.model

import com.google.gson.annotations.SerializedName

data class ImgsRespone(@SerializedName("ImgsModel")
                        val results:List<ImgsModel>)

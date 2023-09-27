package com.sarrawi.img.db.repository

import com.sarrawi.img.Api.ApiService

class ImgRepository(val apiService: ApiService) {

    suspend fun getImgs_Repo(ID_Type_id: Int) = apiService.getImgs_Ser(ID_Type_id)

}
package com.sarrawi.img.db.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.sarrawi.img.Api.ApiService


class ImgRepository(val apiService: ApiService,app:Application) {




    suspend fun getImgs_Repo(ID_Type_id: Int) = apiService.getImgs_Ser(ID_Type_id)






}
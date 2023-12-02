package com.sarrawi.img.db.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sarrawi.img.db.repository.ImgRepository

class ViewModelFactory constructor(private val context:Context, private val repository: ImgRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(Imgs_ViewModel::class.java)) {
            Imgs_ViewModel(context, this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}



//class ViewModelFactory2 constructor(private val repository: FavoriteImageRepository):ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if (modelClass.isAssignableFrom(FavoriteImagesViewModel::class.java)) {
//            FavoriteImagesViewModel(this.repository) as T
//        } else {
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
//    }
//
//}
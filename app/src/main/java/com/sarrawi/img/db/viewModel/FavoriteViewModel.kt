package com.sarrawi.img.db.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarrawi.img.db.repository.Fav_Repo
import com.sarrawi.img.db.repository.ImgTypes_Repository
import com.sarrawi.img.model.FavoriteModel
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): ViewModel() {

    private var __response = MutableLiveData<List<FavoriteModel>>()
    val responseImgFav: MutableLiveData<List<FavoriteModel>>
        get() = __response

    private val favRepo: Fav_Repo = Fav_Repo(application)

    fun insert_fav_viewModel(favoriteModel: FavoriteModel)=viewModelScope.launch {
        favRepo.add_fav_repo(favoriteModel)
    }



//    // update msg_table items favorite state
//    fun update_fav(id: Int,state:Boolean) = viewModelScope.launch {
//        favRepo.update_fav(id,state)
//    }

    fun getFav_viewModel(): LiveData<List<FavoriteModel>> {
        Log.e("tessst","entred22")
        viewModelScope.launch {
            favRepo.getAllFav()
        }
        return __response
    }

    // delete favorite item from db
    fun delete_fav_viewModel(fav: FavoriteModel)= viewModelScope.launch {
        favRepo.deleteFav(fav)
    }

}
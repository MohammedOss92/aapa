package com.sarrawi.img.db.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarrawi.img.db.Dao.Favorite_Dao
import com.sarrawi.img.db.repository.Fav_Repo
import com.sarrawi.img.db.repository.ImgTypes_Repository
import com.sarrawi.img.model.FavoriteModel
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): ViewModel() {

    private var __response = MutableLiveData<List<FavoriteModel>>()
    val responseImgFav: MutableLiveData<List<FavoriteModel>>
        get() = __response

    private val _favoriteLiveData = MutableLiveData<FavoriteModel?>()
    val favoriteLiveData: LiveData<FavoriteModel?>
        get() = _favoriteLiveData

    private val favRepo: Fav_Repo = Fav_Repo(application)

    private lateinit var favoriteDao: Favorite_Dao




}
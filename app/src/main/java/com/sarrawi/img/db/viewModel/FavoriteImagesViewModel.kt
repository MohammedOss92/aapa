package com.sarrawi.img.db.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.model.FavoriteImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteImagesViewModel(private val repository: FavoriteImageRepository) : ViewModel() {

    private val _favoriteImages = MutableLiveData<List<FavoriteImage>>()
    val favoriteImages: LiveData<List<FavoriteImage>> = _favoriteImages

    private val _favorite = MutableLiveData<List<FavoriteImage>>()
    val favorite: LiveData<List<FavoriteImage>> = _favorite

    private var __response = MutableLiveData<List<FavoriteImage>>()
    val responseMsgsFav: MutableLiveData<List<FavoriteImage>>
        get() = __response
    init {
        // Initialize _favoriteImages with data from the repository
        viewModelScope.launch(Dispatchers.IO) {
            val images = repository.getAllFavoriteImages()
            _favoriteImages.postValue(images)
        }
    }

    fun addFavoriteImage(favoriteImage: FavoriteImage) {
        println("Adding favorite image: $favoriteImage")
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavoriteImage(favoriteImage)
            // Update _favoriteImages after adding a new image
            val images = repository.getAllFavoriteImages()
            _favoriteImages.postValue(images)
            println("Favorite image added successfully.")
        }
    }

    fun removeFavoriteImage(favoriteImage: FavoriteImage) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavoriteImage(favoriteImage)
            // Update _favoriteImages after removing an image
            val images = repository.getAllFavoriteImages()
            _favoriteImages.postValue(images)
        }
    }

    fun updateImages() {
        viewModelScope.launch {
            val images = repository.getAllFavoriteImages()
            println("Favorite images from the database: $images")
            _favoriteImages.postValue(images)
        }
    }

    fun getAllFavoriteImages() {
        viewModelScope.launch {
            val images = repository.getAllFavoriteImages()
            println("Favorite images from the database: $images")
            _favoriteImages.postValue(images)
        }
    }

//    fun getAllFav(): LiveData<List<FavoriteImage>> {
//
//
//
//        return repository.getAllFavorite()
//    }

    fun getAllFav(): Flow<PagingData<FavoriteImage>> {
        return Pager(PagingConfig(pageSize = 10)) {
            repository.getAllFavorite()
        }.flow.cachedIn(viewModelScope)
    }


    fun getAllFava(): LiveData<List<FavoriteImage>> {

        return repository.getAllFav()
    }

    fun getFavByIDModels(id:Int): LiveData<List<FavoriteImage>> {

        return repository.getFavByIDRepo(id)
    }


    // This method should be called after adding or removing images
//    public fun updateImages() {
//        viewModelScope.launch { val images = repository.getAllFavoriteImages()
//            _favoriteImages.postValue(images)
//
//            // Update _updatedImages with is_fav property
//            val updatedImagesList = images.map { it.copy(is_fav = true) }
//            _updatedImages.postValue(updatedImagesList)
//        }




}







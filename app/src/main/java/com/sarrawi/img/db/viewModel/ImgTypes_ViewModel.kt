package com.sarrawi.img.db.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sarrawi.img.db.repository.ImgTypes_Repository
import com.sarrawi.img.model.Img_Types_model
import kotlinx.coroutines.launch

class ImgTypes_ViewModel(application: Application):ViewModel() {

    private val imgtypesRepository:ImgTypes_Repository = ImgTypes_Repository(application)

    fun insert_Types_ViewModel(imgTypesModel: Img_Types_model) = viewModelScope.launch{
        imgtypesRepository.insert_Types_repo(imgTypesModel)

    }



    fun getAllTypes_ViewModel():LiveData<List<Img_Types_model>> = imgtypesRepository.getAllTypes()

    class ImgTypesViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ImgTypes_ViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ImgTypes_ViewModel(application) as T
            }
            throw IllegalArgumentException("Unable Constructore View Model")
        }
    }
}
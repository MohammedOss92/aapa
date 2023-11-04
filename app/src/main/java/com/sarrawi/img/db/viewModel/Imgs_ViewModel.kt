package com.sarrawi.img.db.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.utils.NetworkConnection
import kotlinx.coroutines.launch


class Imgs_ViewModel(private val context: Context,  private val imgsRepo:ImgRepository ): ViewModel() {


    private val retrofitService = ApiService.provideRetrofitInstance()

    private val _response = MutableLiveData<List<ImgsModel>>()
    private val _responseWithTitle= MutableLiveData<List<ImgsModel>>()
    private val _isLoading = MutableLiveData<Boolean>()

////
    private val _images = MutableLiveData<List<ImgsModel>>()



    val images: LiveData<List<ImgsModel>>
        get() = _images
    ///////


    val isLoading: LiveData<Boolean>
        get() = _isLoading


////////////////////////////////////
   // check Network with Live data
private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected


    fun checkNetworkConnection(applicationContext: Context) {
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observeForever { isConnected ->
            _isConnected.value = isConnected
        }
    }




    fun loadImages(ID_Type_id: Int, startIndex: Int, itemsPerPage: Int): LiveData<List<ImgsModel>> {
        val _images = MutableLiveData<List<ImgsModel>>() // تعريف _images كمتغير LiveData
        _isLoading.postValue(true)

        viewModelScope.launch {
            try {
                val response = imgsRepo.getImgs_Repoa(ID_Type_id, startIndex, itemsPerPage)

                if (response.isSuccessful) {
                    val results = response.body()?.results
                    if (results != null) {
                        _images.postValue(results)
                    }
                } else {
                    Log.i("TestRoom", "loadImages: data corrupted")
                    Log.d("tag", "loadImages Error: ${response.code()}")
                    Log.d("tag", "loadImages: ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e("TestRoom", "loadImages: Error: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
        return _images
    }




    fun getAllImgsViewModel(ID_Type_id: Int): LiveData<List<ImgsModel>> {
        _isLoading.postValue(true) // عرض ProgressBar قبل بدء التحميل

        val _response = MutableLiveData<List<ImgsModel>>()

        viewModelScope.launch {
            try {
                val response = imgsRepo.getImgs_Repo(ID_Type_id)

                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _response.postValue(results)
                    Log.i("TestRoom", "getAllImgs: posts $results")
//                    imgsRepo.insert_imgs_repo(response.body()?.results)
                } else {
                    Log.i("TestRoom", "getAllImgs: data corrupted")
                    Log.d("tag", "getAll Error: ${response.code()}")
                    Log.d("tag", "getAll: ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e("TestRoom", "getAllImgs: Error: ${e.message}")
            } finally {
                _isLoading.postValue(false) // إخفاء ProgressBar بعد انتهاء التحميل
            }
        }

        return _response
    }

    fun getImgsData(ID_Type_id: Int, page: Int): LiveData<List<ImgsModel>> {
        val _response = MutableLiveData<List<ImgsModel>>()

        // قم بإجراء الاستدعاء إلى Retrofit باستخدام viewModelScope.launch
        viewModelScope.launch {
            try {
                val response = imgsRepo.getImgsData(ID_Type_id, page)
                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _response.postValue(results)
                    Log.i("TestRoom", "getAllImgs: posts $results")
                    //                    imgsRepo.insert_imgs_repo(response.body()?.results)

                } else {
                    Log.i("TestRoom", "getAllImgs: data corrupted")
                    Log.d("tag", "getAll Error: ${response.code()}")
                    Log.d("tag", "getAll: ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e("TestRoom", "getAllImgs: Error: ${e.message}")
            }
        }

        return _response
    }


//    fun getImgsData(ID_Type_id: Int, page: Int): LiveData<List<ImgsModel>> {
//        val _response = MutableLiveData<List<ImgsModel>>()
//        viewModelScope.launch {
//            try {
//                val response = imgsRepo.getImgsData(ID_Type_id, page)
//                if (response.isSuccessful) {
//                    val results = response.body()?.results
//                    _response.postValue(results)
//                    Log.i("TestRoom", "getAllImgs: posts $results")
////                    imgsRepo.insert_imgs_repo(response.body()?.results)
//                } else {
//                    Log.i("TestRoom", "getAllImgs: data corrupted")
//                    Log.d("tag", "getAll Error: ${response.code()}")
//                    Log.d("tag", "getAll: ${response.body()}")
//                }
//            } catch (e: Exception) {
//                Log.e("TestRoom", "getAllImgs: Error: ${e.message}")
//            }
//        }
//        return _response
//    }




}
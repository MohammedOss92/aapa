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




    suspend fun getAllImgs_ViewModel(ID_Type_id:Int) :MutableLiveData<List<ImgsModel>> {

            imgsRepo.getImgs_Repo(ID_Type_id).let { response ->

                if (response.isSuccessful) {
                    _response.postValue(response.body()?.results)
                    Log.i("TestRoom", "getAllImgs: posts ${response.body()?.results}")
                } else {
                    Log.i("TestRoom", "getAllImgs: data corrupted")
                    Log.d("tag", "getAll Error: ${response.code()}")
                    Log.d("tag", "getAll: ${response.body()}")
                }
            }

            return _response

    }


//    fun getAllImgsViewModel(ID_Type_id: Int): LiveData<List<ImgsModel>> {
//        val _response = MutableLiveData<List<ImgsModel>>()
//
//        viewModelScope.launch {
//            try {
//                val response = imgsRepo.getImgs_Repo(ID_Type_id)
//
//                if (response.isSuccessful) {
//                    _response.postValue(response.body()?.results)
//                    Log.i("TestRoom", "getAllImgs: posts ${response.body()?.results}")
//                } else {
//                    Log.i("TestRoom", "getAllImgs: data corrupted")
//                    Log.d("tag", "getAll Error: ${response.code()}")
//                    Log.d("tag", "getAll: ${response.body()}")
//                }
//            } catch (e: Exception) {
//                Log.e("TestRoom", "getAllImgs: Error: ${e.message}")
//            }
//        }
//
//        return _response
//    }

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
//
//    fun getAllImgsViewModel(ID_Type_id: Int): LiveData<List<ImgsModel>> {
//        _isLoading.postValue(true) // عرض ProgressBar قبل بدء التحميل
//
//        val _response = MutableLiveData<List<ImgsModel>>()
//
//        viewModelScope.launch {
//            try {
//                val response = imgsRepo.getImgs_Repo(ID_Type_id)
//
//                if (response.isSuccessful) {
//                    _response.postValue(response.body()?.results)
//                    Log.i("TestRoom", "getAllImgs: posts ${response.body()?.results}")
//
//                } else
//                {
//                    Log.i("TestRoom", "getAllImgs: data corrupted")
//                    Log.d("tag", "getAll Error: ${response.code()}")
//                    Log.d("tag", "getAll: ${response.body()}")
//                }
//            } catch (e: Exception) {
//                Log.e("TestRoom", "getAllImgs: Error: ${e.message}")
//            } finally {
//                _isLoading.postValue(false) // إخفاء ProgressBar بعد انتهاء التحميل
//            }
//        }
//
//        return _response
//    }




//    // update msg_table items favorite state
//    fun update_fav(id: Int,state:Boolean) = viewModelScope.launch {
//        imgsRepo.update_fav(id,state)
//    }




}
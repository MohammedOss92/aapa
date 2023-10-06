package com.sarrawi.img.db.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarrawi.img.model.ImgsModel

class SharedViewModel : ViewModel() {
    val imgsLiveData = MutableLiveData<List<ImgsModel>>()

    // إضافة دالة لتحديث البيانات
    fun updateImgsData(newData: List<ImgsModel>) {
        imgsLiveData.postValue(newData)
    }
}

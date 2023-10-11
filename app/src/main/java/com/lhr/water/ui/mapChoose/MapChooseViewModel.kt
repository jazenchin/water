package com.lhr.water.ui.mapChoose

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MapChooseViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var currentCategoryIds: MutableLiveData<String> =
            MutableLiveData<String>().apply { postValue("") }
    }
    var title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Water" }
    var userName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Hi XXXXX" }

}
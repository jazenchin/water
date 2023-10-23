package com.lhr.water.ui.formContent

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.ui.base.APP

class FormContentViewModel(context: Context): AndroidViewModel(context.applicationContext as APP) {

    companion object {
        var currentCategoryIds: MutableLiveData<String> =
            MutableLiveData<String>().apply { postValue("") }
    }

}
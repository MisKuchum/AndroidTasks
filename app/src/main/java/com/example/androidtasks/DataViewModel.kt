package com.example.androidtasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataViewModel : ViewModel() {
    val colorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val photoMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
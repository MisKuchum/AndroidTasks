package com.example.androidtasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {
    private var _color = MutableLiveData<String>()
    var color: LiveData<String> = _color

    fun setColor(colorString: String) {
        _color.value = colorString
    }
}
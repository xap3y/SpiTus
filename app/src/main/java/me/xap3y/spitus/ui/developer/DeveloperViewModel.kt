package me.xap3y.spitus.ui.developer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.xap3y.spitus.Utils.DataManager

class DeveloperViewModel(private val dataManager: DataManager) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
}
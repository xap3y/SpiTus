package me.xap3y.spitus.ui.servers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.xap3y.spitus.Utils.DataManager

class ServersViewModel(private val dataManager: DataManager) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "test"
    }
    val text: LiveData<String> = _text
}
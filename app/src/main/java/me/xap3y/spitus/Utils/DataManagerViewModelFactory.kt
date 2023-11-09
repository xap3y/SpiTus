package me.xap3y.spitus.Utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DataManagerViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.Factory {

    /*override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServersViewModel::class.java)) {
            return ServersViewModel(dataManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }*/
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            // Attempt to create an instance of the ViewModel using reflection
            val constructor = modelClass.getConstructor(DataManager::class.java)
            return constructor.newInstance(dataManager)
        } catch (e: NoSuchMethodException) {
            throw IllegalArgumentException("ViewModel class must have a constructor that accepts DataManager")
        }
    }
}
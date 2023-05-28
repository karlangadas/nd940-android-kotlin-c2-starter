package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _property = MutableLiveData<ArrayList<Asteroid>>()
    val property: LiveData<ArrayList<Asteroid>>
        get() = _property

    init {
        getAsteroidsProperties()
    }

    private fun getAsteroidsProperties() {
        viewModelScope.launch {
            try {
                _property.value =
                    parseAsteroidsJsonResult(JSONObject(AsteroidsApi.retrofitService.getPropertiesAsync()))
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }

    }
}
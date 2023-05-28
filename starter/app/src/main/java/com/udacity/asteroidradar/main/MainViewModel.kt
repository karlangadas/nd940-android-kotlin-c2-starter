package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

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
        // TODO
    }
}
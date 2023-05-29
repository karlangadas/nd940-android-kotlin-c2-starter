package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

    val asteroids = asteroidsRepository.asteroids

    fun getTodayAsteroid(): List<Asteroid>? {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        val startDate = dateFormat.format(calendar.time)
        return asteroids.value?.filter { it.closeApproachDate == startDate }
    }

    fun getWeekAsteroid(): List<Asteroid>? {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        val startDate = dateFormat.format(calendar.time)
        while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }
        val endDate = dateFormat.format(calendar.time)
        return asteroids.value?.filter { it.closeApproachDate in startDate..endDate }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshAsteroids()
                _pictureOfTheDay.value =
                    JSONObject(NasaApi.retrofitService.getPictureOfTheDaysAsync()).getString("hdurl")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failure")
            }
        }
    }

    val asteroids = asteroidsRepository.asteroids
    private val _pictureOfTheDay = MutableLiveData<String>()
    val pictureOfTheDay: LiveData<String>
        get() = _pictureOfTheDay

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
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        val endDate = dateFormat.format(calendar.time)
        return asteroids.value?.filter { it.closeApproachDate in startDate..endDate }
    }
}
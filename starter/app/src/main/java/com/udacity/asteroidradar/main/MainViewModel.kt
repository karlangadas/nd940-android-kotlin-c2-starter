package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.NasaApi
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
            try {
                asteroidsRepository.refreshAsteroids()
                val moshi = Moshi.Builder().build()
                val adapter: JsonAdapter<PictureOfDay> = moshi.adapter(PictureOfDay::class.java)
                _pictureOfTheDay.value =
                    adapter.fromJson(NasaApi.retrofitService.getPictureOfTheDaysAsync())
            } catch (e: Exception) {
                _pictureOfTheDay.value = PictureOfDay(
                    "",
                    application.resources.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet),
                    ""
                )
                Log.e("MainViewModel", "Failure")
            }
        }
    }

    val asteroids = asteroidsRepository.asteroids
    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
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
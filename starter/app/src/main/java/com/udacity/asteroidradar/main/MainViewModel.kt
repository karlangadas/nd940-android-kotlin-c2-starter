package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        val (startDate, endDate) = getDates()

        viewModelScope.launch {
            try {
                _property.value =
                    parseAsteroidsJsonResult(
                        JSONObject(
                            AsteroidsApi.retrofitService.getPropertiesAsync(
                                startDate,
                                endDate
                            )
                        )
                    )
                _status.value = "Success"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }

    }

    private fun getDates(): Pair<String, String> {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        val startDate = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
        val endDate = dateFormat.format(calendar.time)
        return Pair(startDate, endDate)
    }
}
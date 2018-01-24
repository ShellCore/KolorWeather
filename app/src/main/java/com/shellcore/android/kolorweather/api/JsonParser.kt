package com.shellcore.android.kolorweather.api

import com.shellcore.android.kolorweather.extensions.iterator
import com.shellcore.android.kolorweather.models.CurrentWeather
import com.shellcore.android.kolorweather.models.Day
import org.json.JSONObject

/**
 * Created by MOGC on 22/01/2018.
 */
fun getWeatherFromJson(response: JSONObject): CurrentWeather {
    val currentlyJson = response.getJSONObject(CURRENTLY)

    with(currentlyJson) {
        val currentWeather = CurrentWeather(
                getString(ICON),
                getString(SUMMARY),
                getDouble(TEMPERATURE),
                getDouble(PRECIPPROBABILITY))

        return currentWeather
    }
}

fun getDailyWeatherFromJson(response: JSONObject): ArrayList<Day> {

    val dailyWeatherJson = response.getJSONObject(DAILY)
    val dayJsonArray = dailyWeatherJson.getJSONArray(DATA)
    val days = ArrayList<Day>()

    for (dayJson in dayJsonArray) {
        val minTemp = dayJson.getDouble(TEMPERATUREMIN)
        val maxTemp = dayJson.getDouble(TEMPERATUREMAX)
        val time = dayJson.getLong(TIME)
        days.add(Day(time, minTemp, maxTemp))
    }

    return days
}

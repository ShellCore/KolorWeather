package com.shellcore.android.kolorweather.api

import com.shellcore.android.kolorweather.models.CurrentWeather
import org.json.JSONObject

/**
 * Created by MOGC on 22/01/2018.
 */
class JsonParser {

    fun getWeatherFromJson(response : JSONObject) : CurrentWeather {
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
}
package com.shellcore.android.kolorweather

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.shellcore.android.kolorweather.api.API_KEY
import com.shellcore.android.kolorweather.api.DARK_SKY_URL
import com.shellcore.android.kolorweather.api.JsonParser
import com.shellcore.android.kolorweather.models.CurrentWeather
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName
    val jsonParser = JsonParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val latitude = 19.358468
        val longitude = -99.194853
        val lang = getString(R.string.language) // lenguaje español
        val units = getString(R.string.units) // Sistema internacional

        val url = "${DARK_SKY_URL}/${API_KEY}/${latitude},${longitude}?lang=${lang}&units=${units}"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> { response ->

                    val responseJson = JSONObject(response)
                    val currentWeather = jsonParser.getWeatherFromJson(responseJson)
                    buildCurrentWeatherUI(currentWeather)

                },
                Response.ErrorListener {
                    Log.i(TAG, "Error en la llamada")

                }
        )

        queue.add(stringRequest)
    }

    private fun buildCurrentWeatherUI(currentWeather: CurrentWeather) {
        val precipProbability = (currentWeather.precip * 100).toInt()
        txtHumidity.text = getString(R.string.precip_placeholder, precipProbability)
        txtTemperature.text = getString(R.string.temp_placeholder, currentWeather.temp.toInt())
        txtWeather.text = currentWeather.summary

        imgWeather.setImageDrawable(ResourcesCompat.getDrawable(resources, currentWeather.getIconResource(), null))
    }
}

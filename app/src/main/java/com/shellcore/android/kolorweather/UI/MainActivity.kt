package com.shellcore.android.kolorweather.UI

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.shellcore.android.kolorweather.R
import com.shellcore.android.kolorweather.api.API_KEY
import com.shellcore.android.kolorweather.api.DARK_SKY_URL
import com.shellcore.android.kolorweather.api.JsonParser
import com.shellcore.android.kolorweather.models.CurrentWeather
import com.shellcore.android.kolorweather.models.Day
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName
    val jsonParser = JsonParser()
    var days : ArrayList<Day> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtHumidity.text = getString(R.string.precip_placeholder, 0)
        txtTemperature.text = getString(R.string.temp_placeholder, 0)

        getWeather()
    }

    fun startHourlyActivity(view : View) {
        val intent = Intent(this, HourlyWeatherActivity::class.java)
        startActivity(intent)
    }

    fun startDailyActivity(view : View) {
        val intent = Intent(this, DailyWeatherActivity::class.java).apply {
            putParcelableArrayListExtra(DailyWeatherActivity.DAILY_WEATHER_KEY, days)
        }
        startActivity(intent)
    }

    private fun getWeather() {
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

                    days = jsonParser.getDailyWeather(responseJson)

                    buildCurrentWeatherUI(currentWeather)

                },
                Response.ErrorListener {
                    displayErrorMessage()
                }
        )

        queue.add(stringRequest)
    }

    private fun displayErrorMessage() {
        val snackbar = Snackbar.make(main_container, getString(R.string.connection_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.refresh), {
                    getWeather()
                })
                .show()
    }

    private fun buildCurrentWeatherUI(currentWeather: CurrentWeather) {
        val precipProbability = (currentWeather.precip * 100).toInt()
        txtHumidity.text = getString(R.string.precip_placeholder, precipProbability)
        txtTemperature.text = getString(R.string.temp_placeholder, currentWeather.temp.toInt())
        txtWeather.text = currentWeather.summary

        imgWeather.setImageDrawable(ResourcesCompat.getDrawable(resources, currentWeather.getIconResource(), null))
    }
}

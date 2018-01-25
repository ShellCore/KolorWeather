package com.shellcore.android.kolorweather.UI

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.LocationServices
import com.shellcore.android.kolorweather.R
import com.shellcore.android.kolorweather.api.*
import com.shellcore.android.kolorweather.extensions.action
import com.shellcore.android.kolorweather.extensions.displaySnack
import com.shellcore.android.kolorweather.models.CurrentWeather
import com.shellcore.android.kolorweather.models.Day
import com.shellcore.android.kolorweather.models.Hour
import kotlinx.android.synthetic.main.activity_daily_weather.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName
    val MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1
    var days: ArrayList<Day> = ArrayList()
    var hours: ArrayList<Hour> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtHumidity.text = getString(R.string.precip_placeholder, 0)
        txtTemperature.text = getString(R.string.temp_placeholder, 0)

        checkWeather()
    }

    fun startHourlyActivity(view: View) {
        val intent = Intent(this, HourlyWeatherActivity::class.java).apply {
            putParcelableArrayListExtra(HourlyWeatherActivity.HOURLY_WEATHER_KEY, hours)
        }
        startActivity(intent)
    }

    fun startDailyActivity(view: View) {
        val intent = Intent(this, DailyWeatherActivity::class.java).apply {
            putParcelableArrayListExtra(DailyWeatherActivity.DAILY_WEATHER_KEY, days)
        }
        startActivity(intent)
    }

    private fun checkWeather() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            var latitude = 0.0
            var longitude = 0.0
            val locationClient = LocationServices.getFusedLocationProviderClient(this)
            locationClient.lastLocation.addOnSuccessListener { location ->
                latitude = location.latitude
                longitude = location.longitude
            }

            val lang = getString(R.string.language) // lenguaje español
            val units = getString(R.string.units) // Sistema internacional

            val url = "${DARK_SKY_URL}/${API_KEY}/${latitude},${longitude}?lang=${lang}&units=${units}"

            val queue = Volley.newRequestQueue(this)

            val stringRequest = StringRequest(
                    Request.Method.GET,
                    url,
                    Response.Listener<String> {
                        val responseJson = JSONObject(it)
                        val currentWeather = getWeatherFromJson(responseJson)
                        days = getDailyWeatherFromJson(responseJson)
                        hours = getHourlyWeatherFromJson(responseJson)
                        buildCurrentWeatherUI(currentWeather)

                    },
                    Response.ErrorListener {
                        displayErrorMessage()
                    }
            )

            queue.add(stringRequest)
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkWeather()
                } else {
                    container.displaySnack(message = "No se encuentra el permiso requerido para poder obtener la ubicación.", func = {})
                }
                return
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun displayErrorMessage() {
        main_container.displaySnack(getString(R.string.connection_error), Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.refresh), {
                checkWeather()
            })
        }
    }

    private fun buildCurrentWeatherUI(currentWeather: CurrentWeather) {
        val precipProbability = (currentWeather.precip * 100).toInt()
        txtHumidity.text = getString(R.string.precip_placeholder, precipProbability)
        txtTemperature.text = getString(R.string.temp_placeholder, currentWeather.temp.toInt())
        txtWeather.text = currentWeather.summary

        imgWeather.setImageDrawable(ResourcesCompat.getDrawable(resources, currentWeather.getIconResource(), null))
    }
}

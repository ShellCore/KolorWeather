package com.shellcore.android.kolorweather.UI

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shellcore.android.kolorweather.R
import com.shellcore.android.kolorweather.adapters.HourAdapter
import com.shellcore.android.kolorweather.models.Hour
import kotlinx.android.synthetic.main.activity_hourly_weather.*

class HourlyWeatherActivity : AppCompatActivity() {

    companion object {
        const val HOURLY_WEATHER_KEY = "hourly_weather_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourly_weather)

        val hours: ArrayList<Hour> = intent.getParcelableArrayListExtra(HOURLY_WEATHER_KEY)
        val hourAdapter = HourAdapter(this, hours)

        lsthourly.adapter = hourAdapter

        lsthourly.emptyView = txtNoDataHour
    }
}

package com.shellcore.android.kolorweather.UI

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.shellcore.android.kolorweather.R
import com.shellcore.android.kolorweather.adapters.HourRecyclerViewAdapter
import com.shellcore.android.kolorweather.models.Hour
import kotlinx.android.synthetic.main.activity_hourly_weather.*

class HourlyWeatherActivity : AppCompatActivity() {

    companion object {
        const val HOURLY_WEATHER_KEY = "hourly_weather_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourly_weather)

        recHourly.layoutManager = LinearLayoutManager(this)

        intent.let {

            val hours: ArrayList<Hour> = it.getParcelableArrayListExtra(HOURLY_WEATHER_KEY)
            recHourly.adapter = HourRecyclerViewAdapter(hours)
        }

    }
}

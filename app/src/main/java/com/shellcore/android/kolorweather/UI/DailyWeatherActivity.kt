package com.shellcore.android.kolorweather.UI

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shellcore.android.kolorweather.R
import com.shellcore.android.kolorweather.adapters.DayAdapter
import com.shellcore.android.kolorweather.models.Day
import kotlinx.android.synthetic.main.activity_daily_weather.*

class DailyWeatherActivity : AppCompatActivity() {

    companion object {
        const val DAILY_WEATHER_KEY = "daily_weather_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_weather)

        val days: ArrayList<Day> = intent.getParcelableArrayListExtra(DAILY_WEATHER_KEY)
        val dayAdapter = DayAdapter(this, days)

        lstDaily.adapter = dayAdapter

        lstDaily.emptyView = txtNoData
    }
}

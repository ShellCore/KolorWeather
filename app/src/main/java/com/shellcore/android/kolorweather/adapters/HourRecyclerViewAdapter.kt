package com.shellcore.android.kolorweather.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.shellcore.android.kolorweather.R
import com.shellcore.android.kolorweather.extensions.inflate
import com.shellcore.android.kolorweather.models.Hour
import kotlinx.android.synthetic.main.hourly_item.view.*

/**
 * Created by MOGC on 25/01/2018.
 */
class HourRecyclerViewAdapter(val hours: ArrayList<Hour>) : RecyclerView.Adapter<HourRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(parent.inflate(R.layout.hourly_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(hours[position])

    override fun getItemCount()
            = hours.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hour: Hour) = with(itemView) {
            txtHour.text = hour.getFormattedTime()
            txtHourTemp.text = itemView.context.getString(R.string.temp_placeholder, hour.temp.toInt())
            txtHourPrecip.text = itemView.context.getString(R.string.precip_placeholder, hour.precip.toInt())
        }
    }
}
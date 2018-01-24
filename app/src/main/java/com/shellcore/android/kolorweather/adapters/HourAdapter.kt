package com.shellcore.android.kolorweather.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shellcore.android.kolorweather.R
import com.shellcore.android.kolorweather.extensions.inflate
import com.shellcore.android.kolorweather.models.Hour

/**
 * Created by MOGC on 24/01/2018.
 */
class HourAdapter(val context : Context, val dataSource : ArrayList<Hour>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder : ViewHolder
        val view : View
        if (convertView == null) {
            view = parent.inflate(R.layout.hourly_item)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            view = convertView
        }

        val currentHour = dataSource[position]

        holder.apply {
            txtHour.text = currentHour.getFormattedTime()
            txtHourTemp.text = context.getString(R.string.temp_placeholder, currentHour.temp.toInt())
            val precipProbability = (currentHour.precip * 100).toInt()
            txtHourPrecip.text = context.getString(R.string.precip_placeholder, precipProbability)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    private class ViewHolder(v : View) {

        val txtHour : TextView = v.findViewById(R.id.txtHour)
        val txtHourTemp : TextView = v.findViewById(R.id.txtHourTemp)
        val txtHourPrecip : TextView = v.findViewById(R.id.txtHourPrecip)


    }
}
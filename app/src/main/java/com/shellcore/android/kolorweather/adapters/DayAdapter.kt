package com.shellcore.android.kolorweather.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shellcore.android.kolorweather.R
import com.shellcore.android.kolorweather.extensions.inflate
import com.shellcore.android.kolorweather.models.Day

/**
 * Created by MOGC on 24/01/2018.
 */
class DayAdapter(val context : Context, val dataSource : ArrayList<Day>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder : ViewHolder
        val view : View
        if (convertView == null) {
            view = parent.inflate(R.layout.daily_item)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            view = convertView
        }

        val currentDay = dataSource[position]

        holder.apply {
            txtDay.text = currentDay.getFormattedTime()
            txtMin.text = "Min " + context.getString(R.string.temp_placeholder, currentDay.minTemp.toInt())
            txtMax.text = "Max " + context.getString(R.string.temp_placeholder, currentDay.maxTemp.toInt())
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

        val txtDay : TextView = v.findViewById(R.id.txtDay)
        val txtMin : TextView = v.findViewById(R.id.txtMin)
        val txtMax : TextView = v.findViewById(R.id.txtMax)


    }
}
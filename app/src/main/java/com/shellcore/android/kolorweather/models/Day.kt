package com.shellcore.android.kolorweather.models

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by MOGC on 24/01/2018.
 */
data class Day(val time : Long,
          val minTemp : Double,
          val maxTemp : Double) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readDouble(),
            parcel.readDouble())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(time)
        dest.writeDouble(minTemp)
        dest.writeDouble(maxTemp)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getFormattedTime() : String {
        val formatted = SimpleDateFormat("EEEE", Locale.US)
        val date = Date(time * 1000)
        val dateOfWeek = formatted.format(date)
        return dateOfWeek
    }

    companion object CREATOR : Parcelable.Creator<Day> {
        override fun createFromParcel(parcel: Parcel): Day {
            return Day(parcel)
        }

        override fun newArray(size: Int): Array<Day?> {
            return arrayOfNulls(size)
        }
    }
}
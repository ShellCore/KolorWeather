package com.shellcore.android.kolorweather.models

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by MOGC on 24/01/2018.
 */
data class Hour(val time : Long,
                val temp : Double,
                val precip : Double) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readDouble(),
            parcel.readDouble())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(time)
        dest.writeDouble(temp)
        dest.writeDouble(precip)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getFormattedTime(): String {
        val formatted = SimpleDateFormat("HH:mm", Locale.US)
        val date = Date(time * 1000)
        val dateOfWeek = formatted.format(date)
        return dateOfWeek
    }

    companion object CREATOR : Parcelable.Creator<Hour> {
        override fun createFromParcel(parcel: Parcel): Hour {
            return Hour(parcel)
        }

        override fun newArray(size: Int): Array<Hour?> {
            return arrayOfNulls(size)
        }
    }
}
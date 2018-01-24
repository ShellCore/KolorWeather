package com.shellcore.android.kolorweather.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by MOGC on 24/01/2018.
 */

operator fun JSONArray.iterator() : Iterator<JSONObject> =
        (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()

fun ViewGroup.inflate(resource : Int) : View = LayoutInflater.from(context).inflate(resource, this, false)
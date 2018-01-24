package com.shellcore.android.kolorweather.extensions

import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by MOGC on 24/01/2018.
 */

operator fun JSONArray.iterator(): Iterator<JSONObject> =
        (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()

fun ViewGroup.inflate(resource: Int): View = LayoutInflater.from(context).inflate(resource, this, false)

fun View.displaySnack(message: String, length: Int = Snackbar.LENGTH_SHORT, func: Snackbar.() -> Unit) {
    val snackbar = Snackbar.make(this, message, length)
    snackbar.func()
    snackbar.show()
}

fun Snackbar.action(message: String, listener: (View) -> Unit) {
    setAction(message, listener)
}
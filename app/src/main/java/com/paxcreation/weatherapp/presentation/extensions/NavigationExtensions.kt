package com.paxcreation.weatherapp.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.navigateActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

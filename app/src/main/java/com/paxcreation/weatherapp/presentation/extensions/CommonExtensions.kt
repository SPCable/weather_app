package com.paxcreation.weatherapp.presentation.extensions

inline fun <reified T> genericCast(anything: Any): T {
    return anything as T
}

inline fun <reified T> genericCastOrNull(anything: Any?): T? {
    return anything as? T
}



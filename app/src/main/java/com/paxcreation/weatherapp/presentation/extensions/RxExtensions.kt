package com.paxcreation.weatherapp.presentation.extensions

import android.view.View
import com.paxcreation.weatherapp.domain.`interface`.PlainConsumer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.functions.BiFunction


fun <T> onSuccess(consumer: (T) -> Unit) = object : PlainConsumer<T> {
    override fun accept(t: T) {
        consumer.invoke(t)
    }
}


fun <T> onError(consumer: (T) -> Unit) = object : PlainConsumer<T> {
    override fun accept(t: T) {
        consumer.invoke(t)
    }
}

fun onClickListener(onClick: () -> Unit) = View.OnClickListener {
    onClick.invoke()
}

fun <T, U> Single<T>.zipWithX(other: SingleSource<U>): Single<Pair<T, U>>
        = zipWith(other, BiFunction { t, u -> Pair(t,u) })
package com.paxcreation.weatherapp.presentation.utils.rx

import io.reactivex.rxjava3.annotations.Nullable
import retrofit2.Response

class Result<T> private constructor(
    private val response: @Nullable Response<T>?,
    private val error: @Nullable Throwable?
) {


    fun response(): @Nullable Response<T>? {
        return response
    }


    fun error(): @Nullable Throwable? {
        return error
    }


    fun isError(): Boolean {
        return error != null
    }

    companion object {
        fun <T> error(error: Throwable?): Result<T> {
            if (error == null) throw NullPointerException("error == null")
            return Result(null, error)
        }

        fun <T> response(response: Response<T>?): Result<T> {
            if (response == null) throw NullPointerException("response == null")
            return Result(response, null)
        }
    }

}
package com.paxcreation.weatherapp.presentation.di

import android.content.Context
import android.util.Log
import com.paxcreation.weatherapp.BuildConfig
import com.paxcreation.weatherapp.data.services.ApiService
import com.paxcreation.weatherapp.domain.config.AppConfig
import com.paxcreation.weatherapp.domain.manager.UserManager
import com.paxcreation.weatherapp.presentation.utils.ServiceResponseConverter
import com.paxcreation.weatherapp.presentation.utils.rx.RxJava3CallAdapterFactory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        @ApplicationContext context: Context,
        userManager: UserManager,
        gSon: Gson
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val builder = generateNewRequestBuilder(
                    context,
                    chain,
                    userManager
                )
                var response = chain.proceed(builder.build())
                response

            }
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    private fun generateNewRequestBuilder(
        context: Context,
        chain: Interceptor.Chain,
        userManager: UserManager
    ): Request.Builder {
        val token = userManager.getUserToken()
        val original = chain.request()
        val originalHttpUrl = original.url
        val builderUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("app_code", AppConfig.APP_CODE)
            .addQueryParameter("lang", Locale.getDefault().language)
        val url = builderUrl.build()
        val builder = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .url(url)
        if (!token.isNullOrEmpty()) {
            builder.addHeader(
                "Authorization", "Bearer $token"
            )
        }
        return builder
    }


    @Provides
    @Singleton
    internal fun provideApiService(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.metaweather.com" + "/api/")
            .client(okHttpClient)
            .addConverterFactory(ServiceResponseConverter(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
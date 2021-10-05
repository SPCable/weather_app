package com.paxcreation.weatherapp.presentation.di

import android.content.Context
import com.paxcreation.weatherapp.App
import com.paxcreation.weatherapp.data.services.SocketManager
import com.paxcreation.weatherapp.domain.manager.UserManager
import com.paxcreation.weatherapp.presentation.utils.NavigatorHelper
import com.paxcreation.weatherapp.presentation.utils.SafeTypeAdapterFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.lang.reflect.Modifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNavigatorHelper(userManager: UserManager) = NavigatorHelper(userManager)

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Provides
    @Singleton
    fun provideSocketManager(
        @ApplicationContext context: Context,
        gson: Gson,
        userManager: UserManager
    ): SocketManager {
        return SocketManager(context, gson, userManager)
    }

    @Provides
    @Singleton
    fun provideGSon(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapterFactory(SafeTypeAdapterFactory)
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()
}


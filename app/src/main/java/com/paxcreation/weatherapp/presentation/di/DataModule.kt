package com.paxcreation.weatherapp.presentation.di

import android.content.Context
import androidx.room.Room
import com.paxcreation.weatherapp.data.manager.ErrorManager
import com.paxcreation.weatherapp.data.manager.ResourceManager
import com.paxcreation.weatherapp.data.manager.UserManagerImpl
import com.paxcreation.weatherapp.data.repo.LocationRepoImpl
import com.paxcreation.weatherapp.domain.manager.UserManager
import com.google.gson.Gson
import com.paxcreation.weatherapp.data.manager.DatabaseManager
import com.paxcreation.weatherapp.presentation.utils.AppConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context) = ResourceManager(context)

    @Provides
    @Singleton
    fun provideErrorManager(resourceManager: ResourceManager) = ErrorManager(resourceManager)

    @Provides
    @Singleton
    fun provideUserManager(): UserManager = UserManagerImpl()


    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context, gson: Gson): DatabaseManager {
        AppConverters.initialize(gson)
        return Room.databaseBuilder(context, DatabaseManager::class.java, "app-database")
            .fallbackToDestructiveMigration()
            .build()
    }

}
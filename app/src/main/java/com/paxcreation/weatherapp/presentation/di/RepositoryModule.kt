package com.paxcreation.weatherapp.presentation.di

import com.paxcreation.weatherapp.data.repo.LocationRepoImpl
import com.paxcreation.weatherapp.data.repo.LocationSearchRepoImpl
import com.paxcreation.weatherapp.data.repo.UserRepoImpl
import com.paxcreation.weatherapp.domain.repo.LocationRepo
import com.paxcreation.weatherapp.domain.repo.LocationSearchRepo
import com.paxcreation.weatherapp.domain.repo.UserRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindLocationRep(repoImpl: LocationRepoImpl): LocationRepo


    @Singleton
    @Binds
    abstract fun bindLocationSearchRep(repoImpl: LocationSearchRepoImpl): LocationSearchRepo


    @Singleton
    @Binds
    abstract fun bindUserRep(repoImpl: UserRepoImpl): UserRepo

}

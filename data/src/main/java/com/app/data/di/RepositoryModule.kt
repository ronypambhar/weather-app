package com.app.data.di

import com.app.data.BuildConfig
import com.app.data.local.CitiesData
import com.app.data.network.IOpenWeatherApi
import com.app.data.repository.CitiesRepositoryImpl
import com.app.data.repository.OpenWeatherRepositoryImpl
import com.app.domain.repositories.ICitiesRepository
import com.app.domain.repositories.IOpenWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCitiesRepository(citiesData: CitiesData): ICitiesRepository {
        return CitiesRepositoryImpl(citiesData)
    }

    @Provides
    @Singleton
    fun provideOpenWeatherRepository(iOpenWeatherApi: IOpenWeatherApi): IOpenWeatherRepository {
        return OpenWeatherRepositoryImpl(
            iOpenWeatherApi = iOpenWeatherApi,
            openWeatherKey = BuildConfig.API_KEY
        )
    }

}
package com.app.data.di

import com.app.data.utils.ConnectivityObserver
import com.app.data.utils.ConnectivityObserverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    internal abstract fun bindsConnectivityObserver(
        connectivityObserver: ConnectivityObserverImpl,
    ): ConnectivityObserver
}
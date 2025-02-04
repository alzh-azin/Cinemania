package com.example.core.data.di

import com.example.core.data.util.ConnectivityObserver
import com.example.core.data.util.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkStatusModule {
    @Singleton
    @Binds
    abstract fun bindConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver)
            : ConnectivityObserver
}
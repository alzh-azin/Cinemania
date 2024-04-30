package com.example.cinemania.core.data.di

import com.example.cinemania.core.data.util.NetworkConnectivityObserver
import com.example.cinemania.core.network.utils.ConnectivityObserver
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
package com.example.cinemania.core.data.repository

import com.example.cinemania.core.data.util.NetworkConnectivityObserver
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.domain.repository.CinemaniaRepository
import com.example.cinemania.core.network.model.MediaNetwork
import com.example.cinemania.core.network.model.toMedia
import com.example.cinemania.core.network.service.CinemaniaRemoteDataSource
import com.example.cinemania.core.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaniaRepositoryImpl @Inject constructor(
    private val cinemaniaRemoteDataSource: CinemaniaRemoteDataSource,
    private val connectivityObserver: NetworkConnectivityObserver
) : CinemaniaRepository {

    override fun getTrendingMedia(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Media>> = flow {
        onStart()
        if (connectivityObserver.isConnected()) {
            when (val trendingMediaNetwork = cinemaniaRemoteDataSource.getTrendingMedia()) {
                is NetworkResult.Success -> {
                    emit(trendingMediaNetwork.data?.results?.map(MediaNetwork::toMedia).orEmpty())
                    onComplete()
                }

                is NetworkResult.Error -> {
                    onError("Something went wrong, please try again")
                }

                else -> {
                    onError("Something went wrong, please try again")
                }
            }
        } else {
            onError("No internet connection")
        }
    }

}
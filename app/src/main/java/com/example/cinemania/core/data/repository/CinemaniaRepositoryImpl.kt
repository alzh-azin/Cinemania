package com.example.cinemania.core.data.repository

import com.example.cinemania.core.data.util.NetworkConnectivityObserver
import com.example.cinemania.core.database.dao.CinemaniaLocalDataSource
import com.example.cinemania.core.database.model.toMedia
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.domain.repository.CinemaniaRepository
import com.example.cinemania.core.network.model.toMedia
import com.example.cinemania.core.network.model.toMediaEntity
import com.example.cinemania.core.network.service.CinemaniaRemoteDataSource
import com.example.cinemania.core.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaniaRepositoryImpl @Inject constructor(
    private val cinemaniaLocalDataSource: CinemaniaLocalDataSource,
    private val cinemaniaRemoteDataSource: CinemaniaRemoteDataSource,
    private val connectivityObserver: NetworkConnectivityObserver
) : CinemaniaRepository {

    override fun getTrendingMedia(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Media>> = flow {
        onStart()

        val localList = cinemaniaLocalDataSource.getTrendMovies().map { mediaEntity ->
            mediaEntity.toMedia()
        }
        emit(localList)

        if (connectivityObserver.isConnected()) {
            when (val trendingMediaNetwork = cinemaniaRemoteDataSource.getTrendingMedia()) {
                is NetworkResult.Success -> {

                    val networkList = trendingMediaNetwork.data?.results?.map { mediaNetwork ->
                        mediaNetwork
                    }?.take(TREND_MOVIES_LIST_SIZE)


                    cinemaniaLocalDataSource.insertTrendMovies(networkList?.mapIndexed { index, mediaNetwork ->
                        mediaNetwork.toMediaEntity(
                            isTrendMedia = true,
                            index = index
                        )
                    }.orEmpty())

                    emit(networkList?.map { mediaNetwork ->
                        mediaNetwork.toMedia()
                    }.orEmpty())

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

    override fun getMedia(
        id: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Media> = flow {

        emit(cinemaniaLocalDataSource.getMedia(id).toMedia())
    }

    companion object {

        const val TREND_MOVIES_LIST_SIZE = 10
    }

}
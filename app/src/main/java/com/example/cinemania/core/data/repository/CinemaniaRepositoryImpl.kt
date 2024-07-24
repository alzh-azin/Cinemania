package com.example.cinemania.core.data.repository

import com.example.cinemania.core.data.util.NetworkConnectivityObserver
import com.example.cinemania.core.database.dao.CinemaniaLocalDataSource
import com.example.cinemania.core.database.model.toMedia
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.domain.repository.CinemaniaRepository
import com.example.cinemania.core.network.model.toMediaEntity
import com.example.cinemania.core.network.service.CinemaniaRemoteDataSource
import com.example.cinemania.core.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaniaRepositoryImpl @Inject constructor(
    private val cinemaniaLocalDataSource: CinemaniaLocalDataSource,
    private val cinemaniaRemoteDataSource: CinemaniaRemoteDataSource,
    private val connectivityObserver: NetworkConnectivityObserver
) : CinemaniaRepository {

    override fun getTrendMediaLocal() =
        cinemaniaLocalDataSource.getTrendMovies().map { trendMediaList ->
            trendMediaList.map { mediaEntity ->
                mediaEntity.toMedia()
            }
        }


    override suspend fun getTrendMediaRemote(): NetworkResult<Unit> {

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

                    return NetworkResult.Success(Unit)
                }

                is NetworkResult.Error -> {
                    return NetworkResult.Error(errorMessage = "Something went wrong, please try again")
                }

                else -> {
                    return NetworkResult.Error(errorMessage = "Something went wrong, please try again")
                }
            }
        } else {
            return NetworkResult.Error(errorMessage = "Something went wrong, please try again")
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
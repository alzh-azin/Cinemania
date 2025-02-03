package com.example.cinemania.core.data.repository

import com.example.cinemania.core.utils.CinemaniaConstants
import com.example.core.common.result.NetworkResult
import com.example.core.data.util.NetworkConnectivityObserver
import com.example.core.database.dao.CinemaniaLocalDataSource
import com.example.core.database.model.toMediaEntity
import com.example.core.domain.model.Media
import com.example.core.domain.repository.CinemaniaRepository
import com.example.core.network.model.toMediaEntity
import com.example.core.network.service.CinemaniaRemoteDataSource
import com.example.core.network.service.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@javax.inject.Singleton
class CinemaniaRepositoryImpl @javax.inject.Inject constructor(
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
                    }?.take(CinemaniaConstants.TREND_MOVIES_LIST_SIZE)

                    cinemaniaLocalDataSource.insertTrendMovies(networkList?.mapIndexed { index, mediaNetwork ->
                        mediaNetwork.toMediaEntity(
                            isTrendMedia = true,
                            index = index
                        )
                    }.orEmpty())

                    return NetworkResult.Success(Unit)
                }

                is Error -> {
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

    override suspend fun searchMediaRemote(query: String, pageSize: Int) =
        androidx.paging.Pager(
            config = androidx.paging.PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                SearchPagingSource(
                    query,
                    cinemaniaRemoteDataSource
                )
            }
        ).flow

    override fun getMedia(id: Int): Flow<Media> {
        return cinemaniaLocalDataSource.getMedia(id).map {
            it.toMedia()
        }
    }

    override suspend fun insertMedia(media: Media) {
        cinemaniaLocalDataSource.insertMedia(media.toMediaEntity())
    }

}
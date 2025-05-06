package com.example.core.data.repository

import com.example.core.common.result.NetworkResult
import com.example.core.common.utils.CinemaniaConstants
import com.example.core.data.util.NetworkConnectivityObserver
import com.example.core.database.dao.CinemaniaLocalDataSource
import com.example.core.database.model.FilterTypeEntity
import com.example.core.database.model.toFilterTypeEntity
import com.example.core.database.model.toMediaEntity
import com.example.core.domain.model.FilterType
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.Media
import com.example.core.domain.model.MediaType
import com.example.core.domain.repository.CinemaniaRepository
import com.example.core.network.model.toMedia
import com.example.core.network.service.CinemaniaRemoteDataSource
import com.example.core.network.service.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@javax.inject.Singleton
class CinemaniaRepositoryImpl @Inject constructor(
    private val cinemaniaLocalDataSource: CinemaniaLocalDataSource,
    private val cinemaniaRemoteDataSource: CinemaniaRemoteDataSource,
    private val connectivityObserver: NetworkConnectivityObserver
) : CinemaniaRepository {

    override suspend fun getTrendMediaRemote(): NetworkResult<Unit> {

        if (connectivityObserver.isConnected()) {
            when (val trendingMediaNetwork = cinemaniaRemoteDataSource.getTrendingMedia()) {
                is NetworkResult.Success -> {

                    val networkList = trendingMediaNetwork.data?.results?.map { mediaNetwork ->
                        mediaNetwork
                    }?.take(CinemaniaConstants.TREND_MOVIES_LIST_SIZE)

                    cinemaniaLocalDataSource.insertMedia(
                        filterType = FilterTypeEntity.TrendMedia,
                        mediaList = networkList?.mapIndexed { index, mediaNetwork ->
                            mediaNetwork.toMedia(
                                isTrendMedia = true,
                                index = index
                            ).toMediaEntity()
                        }.orEmpty()
                    )

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

    override suspend fun getTrendMoviesByGenreRemote(genre: GenreType): NetworkResult<Unit> {

        return if (connectivityObserver.isConnected()) {

            when (val trendMoviesByGenre =
                cinemaniaRemoteDataSource.getTrendMoviesByGenre(genre.movieCode)) {
                is NetworkResult.Success -> {

                    cinemaniaLocalDataSource.insertMedia(
                        filterType = FilterTypeEntity.TrendMediaByGenre,
                        genreName = genre.genreName,
                        mediaList = trendMoviesByGenre.data?.results?.map {

                            //mediaType does not exist in the response of this api.
                            // so we have to set it manually
                            it.mediaType = MediaType.MOVIE.value

                            it.toMedia().toMediaEntity()
                        }.orEmpty()

                    )

                    return NetworkResult.Success(Unit)
                }

                is NetworkResult.Error -> {
                    NetworkResult.Error(errorMessage = "Something went wrong, please try again")
                }
            }
        } else {
            return NetworkResult.Error(errorMessage = "Something went wrong, please try again")
        }
    }

    override fun getMedia(id: Int): Flow<Media> {
        return cinemaniaLocalDataSource.getMedia(id).map {
            it.toMedia()
        }
    }

    override fun getMediaByFilterTypeLocal(genre: GenreType?, filterType: FilterType) =
        cinemaniaLocalDataSource.getMedia(
            filterType.toFilterTypeEntity(),
            genre?.genreName.orEmpty()
        ).map { mediaList ->
            mediaList.map { media ->
                media.toMedia()
            }
        }


    override suspend fun insertMedia(media: Media) {
        cinemaniaLocalDataSource.insertMedia(media.toMediaEntity())
    }

}
package com.example.cinemania.core.network.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.network.model.toMedia
import com.example.cinemania.core.network.utils.NetworkResult
import com.example.cinemania.core.network.utils.UrlHelper.BASE_IMAGE_URL_LOW_QUALITY

class SearchPagingSource(
    private val searchQuery: String,
    private val cinemaniaRemoteDataSource: CinemaniaRemoteDataSource,
) : PagingSource<Int, Media>() {
    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {

        return try {
            val page = params.key ?: 1
            val response = cinemaniaRemoteDataSource.searchMedia(page = page, query = searchQuery)

            when (response) {
                is NetworkResult.Success -> {
                    LoadResult.Page(
                        data = response.data?.results?.map { it.toMedia(BASE_IMAGE_URL_LOW_QUALITY) }
                            .orEmpty(),
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.data?.results?.isEmpty() == true) null else page + 1

                    )
                }

                is NetworkResult.Error -> {
                    LoadResult.Error(
                        Throwable()
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
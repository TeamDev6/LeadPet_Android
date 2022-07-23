package com.dev6.data.remote

import android.util.Log
import com.dev6.data.model.adopt.AdoptPaginationResponse
import com.dev6.data.service.FeedAPI
import javax.inject.Inject

interface AdoptRemoteSource {
    suspend fun AdoptAllFeed(page : Int , size : Int): AdoptPaginationResponse?
}

class AdoptRemoteSourceImpl @Inject constructor(
    private val feedService: FeedAPI
) : AdoptRemoteSource {

    override suspend fun AdoptAllFeed(page: Int, size: Int): AdoptPaginationResponse? {
        return feedService.adoptAllFeed(page, size)
    }
}
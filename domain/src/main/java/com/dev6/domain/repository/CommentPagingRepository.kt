package com.dev6.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dev6.domain.entitiyRepo.DonationPostFeed
import com.dev6.domain.entitiyRepo.adopt.AdoptPostFeed
import com.dev6.domain.entitiyRepo.comment.Comment
import com.dev6.domain.entitiyRepo.daily.DailyPostFeed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CommentPagingRepository {
    suspend fun getCommentPagingData(postId: String): Flow<PagingData<Comment>>
}
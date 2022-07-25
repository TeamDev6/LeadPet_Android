package com.dev6.domain.entitiyRepo.daily

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DailyPostFeed(
    @SerializedName("contents")
    val contents: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("normalPostId")
    val normalPostId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: String
) : Serializable
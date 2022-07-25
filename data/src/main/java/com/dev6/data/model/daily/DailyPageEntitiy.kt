package com.dev6.data.model.daily

import com.google.gson.annotations.SerializedName

data class DailyPageEntitiy(
    @SerializedName("page")
    val page: Int,
    @SerializedName("size")
    val size: Int
)
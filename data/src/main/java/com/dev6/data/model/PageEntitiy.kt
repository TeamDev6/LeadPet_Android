package com.dev6.data.model

import com.google.gson.annotations.SerializedName


data class PageEntitiy(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("paged")
    val paged: Boolean,
    @SerializedName("sort")
    val sort: SortEntitiy,
    @SerializedName("unpaged")
    val unpaged: Boolean

)
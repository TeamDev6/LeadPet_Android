package com.dev6.data.model.comment

import com.google.gson.annotations.SerializedName

data class CommentSortEntitiy(
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("sorted")
    val sorted: Boolean,
    @SerializedName("unsorted")
    val unsorted: Boolean
)
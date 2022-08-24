package com.dev6.data.mapper

import com.dev6.data.model.adopt.AdoptFeedEntitiy
import com.dev6.data.model.adopt.AdoptPageEntitiy
import com.dev6.data.model.adopt.AdoptPaginationResponse
import com.dev6.data.entity.adopt.AdoptSortEntitiy
import com.dev6.domain.entitiyRepo.*
import com.dev6.domain.entitiyRepo.adopt.AdoptPostPagenation
import com.dev6.domain.entitiyRepo.adopt.AdoptPostFeed

internal fun AdoptPaginationResponse?.toDomain() = AdoptPostPagenation(
    adoptPostFeed = this?.adoptFeedEntitiyList?.map { it.toData() } ?: listOf(),
    empty = this?.empty ?: false,
    first = this?.first ?: false,
    last = this?.last ?: false,
    number = this?.number ?: 0,
    numberOfElements = this?.numberOfElements ?: 0,
    pageable = this?.adoptPageEntitiy.toDomain(),
    size = this?.size ?: 0,
    sort = this?.adoptSortEntitiy.toDomain(),
    totalElements = this?.totalElements ?: 0,
    totalPages = this?.totalPages ?: 0
)

internal fun AdoptSortEntitiy?.toDomain() = Sort(
    empty = this?.empty ?: false,
    sorted = this?.sorted ?: false,
    unsorted = this?.unsorted ?: false
)
internal fun AdoptPageEntitiy?.toDomain() = Page(
    page = this?.page ?: 0,
    size = this?.size ?: 0
)


internal fun List<AdoptFeedEntitiy>.toDomain(): List<AdoptPostFeed> {
    var temp: ArrayList<AdoptPostFeed> = ArrayList()
    this.forEach {
        temp.add(it.toData())
    }
    return temp
}

internal fun AdoptFeedEntitiy.toData() = AdoptPostFeed(
    contents = contents?: "",
    animalType = animalType ?: "",
    adoptionPostId = adoptionPostId?: "",
    endDate = endDate?: "",
    images = images,
    startDate = startDate?: "",
    title = title?: "",
    userId = userId?: "",
    neutering = neutering?: "",
    species = species?: "",
    gender = gender?: "",
    euthanasiaDate = euthanasiaDate?: "",
    age = age
)

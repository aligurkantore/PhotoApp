package com.codingurkan.photoapp.model

data class PhotoResponseModel(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
) {
    fun hitsIsEmpty() : Boolean {
        return hits.isNotEmpty()
    }
}
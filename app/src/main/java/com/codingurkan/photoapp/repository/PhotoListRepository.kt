package com.codingurkan.photoapp.repository

import com.codingurkan.photoapp.service.ApiService
import com.codingurkan.photoapp.utils.API_KEY
import javax.inject.Inject

class PhotoListRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun photoListRequest(page : Int , perPage : Int) = apiService.photoRequest(null, API_KEY,page,perPage)
}
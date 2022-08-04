package com.codingurkan.photoapp.repository

import com.codingurkan.photoapp.service.ApiService
import com.codingurkan.photoapp.utils.API_KEY
import javax.inject.Inject

class PhotoSearchListRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun searchPhotoListRequest(q : String?,page : Int , perPage : Int) = apiService.photoRequest(q, API_KEY,page,perPage)
}
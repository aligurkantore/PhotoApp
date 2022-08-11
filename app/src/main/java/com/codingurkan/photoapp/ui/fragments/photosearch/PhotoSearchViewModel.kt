package com.codingurkan.photoapp.ui.fragments.photosearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingurkan.photoapp.model.PhotoResponseModel
import com.codingurkan.photoapp.repository.PhotoSearchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoSearchViewModel @Inject constructor(private val repository: PhotoSearchListRepository) : ViewModel() {

    val searchPhotoList = MutableLiveData<PhotoResponseModel>()
    private var job : Job? = null
    val query = MutableLiveData<String>().also { it.value = null }
    private var errorMessage = MutableLiveData<String?>().also { it.value = null }
    val pageNumber = MutableLiveData<Int>().also { it.value = 1 }

    fun downloadSearchPhotos(query : String? , page : Int , perPage : Int){
        job = viewModelScope.launch(Dispatchers.IO){
            val response = repository.searchPhotoListRequest(query , page , perPage)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let { _data ->
                        searchPhotoList.postValue(_data)
                    }
                }else{
                    errorMessage.postValue(response.message())
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
package com.codingurkan.photoapp.ui.fragments.photolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingurkan.photoapp.model.Hit
import com.codingurkan.photoapp.model.PhotoResponseModel
import com.codingurkan.photoapp.repository.PhotoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(private val repository: PhotoListRepository) : ViewModel() {

    private var job : Job? = null
    val photoList = MutableLiveData<PhotoResponseModel>()
    private var errorMessage = MutableLiveData<String?>().also { it.value = null}
    val pageNumber = MutableLiveData<Int>().also { it.value = 1 }

    fun downloadPhotos(page : Int , perPage : Int){
        job = viewModelScope.launch(Dispatchers.IO){
            val response = repository.photoListRequest(page, perPage)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let { _datas ->
                        photoList.postValue(_datas)
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
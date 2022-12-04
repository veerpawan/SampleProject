package com.pawan.sampleproject.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.pawan.sampleproject.data.Repository
import com.pawan.sampleproject.model.DogResponse
import com.pawan.sampleproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (
    private val repository: Repository
) : ViewModel() {

    private val _response: MutableLiveData<NetworkResult<DogResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<DogResponse>> = _response

    fun fetchDogResponse() = viewModelScope.launch {
        repository.getDog().collect { values ->
            _response.value = values
        }
    }

}
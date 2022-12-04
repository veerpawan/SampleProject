package com.pawan.sampleproject.data.remote

import com.pawan.sampleproject.data.remote.DogService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val dogService: DogService) {

    suspend fun getDog() =
        dogService.getDog()

}
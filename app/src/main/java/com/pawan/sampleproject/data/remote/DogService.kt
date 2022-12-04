package com.pawan.sampleproject.data.remote

import com.pawan.sampleproject.model.DogResponse
import com.pawan.sampleproject.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface DogService {

    @GET(Constants.RANDOM_URL)
    suspend fun getDog(): Response<DogResponse>
}

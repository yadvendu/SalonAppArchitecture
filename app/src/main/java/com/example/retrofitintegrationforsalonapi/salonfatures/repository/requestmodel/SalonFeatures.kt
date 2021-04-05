package com.example.retrofitintegrationforsalonapi.salonfatures.repository.requestmodel

import com.example.retrofitintegrationforsalonapi.salonfatures.repository.responsemodel.Features
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SalonFeatures {
    @GET("QA/tlv-feature-visibility")
    fun getSalonFeatures(
        @Query("cityname") cityName: String
    ): Single<Response<Features>>
}
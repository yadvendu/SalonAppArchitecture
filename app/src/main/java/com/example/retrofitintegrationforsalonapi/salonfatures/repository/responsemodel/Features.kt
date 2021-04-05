package com.example.retrofitintegrationforsalonapi.salonfatures.repository.responsemodel


import com.google.gson.annotations.SerializedName

data class Features(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("responseStatus")
    val responseStatus: String
)
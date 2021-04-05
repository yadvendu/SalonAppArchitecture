package com.example.retrofitintegrationforsalonapi.salonfatures.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofitintegrationforsalonapi.R
import com.example.retrofitintegrationforsalonapi.salonfatures.network.*
import com.example.retrofitintegrationforsalonapi.salonfatures.repository.requestmodel.SalonFeatures
import com.example.retrofitintegrationforsalonapi.salonfatures.repository.responsemodel.Features
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var salonApiFeaturesRequest: SalonFeatures
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://armiui6p01.execute-api.ap-south-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        salonApiFeaturesRequest = retrofit.create(SalonFeatures::class.java)
        showResponse()
    }

    private fun makeFeatureRequest(): Single<ApiResponse<Features>> {
        val city = "Bangalore"
        val call = salonApiFeaturesRequest.getSalonFeatures(city)
        return call.map {
            ApiResponse.create(it)
        }.onErrorReturn {
            ApiResponse.create(it)
        }
    }

    private fun getFeaturesResponse(): Single<Resource<ApiResponse<Features>>> {
        return makeFeatureRequest().map {
            when (it) {
                is ApiSuccessResponse -> {
                    Resource.success(it)
                }
                is ApiEmptyResponse -> {
                    Resource.success(it)
                }
                is ApiErrorResponse -> {
                    Resource.error(it.errorMessage, it)
                }
            }
        }
    }

    private fun showResponse() {
        getFeaturesResponse()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        Log.d(
                            "SalonApiResponse",
                            "Success : ${((result.data as ApiSuccessResponse).body as Features)}"
                        )
                    }
                    Resource.Status.ERROR -> {
                        Log.d("SalonApiResponse", "Error : ${result.data.toString()}")
                    }
                    else -> {
                        // Do nothing
                    }
                }
            }
    }
}

package com.hariharan.aboutcanada.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hariharan.aboutcanada.data.model.Facts
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Repository class to fetch the data from server.
 */
class DataRepository {

    /**
     * livedata for response received in rest api.
     */
    private val apiResponseLD: MutableLiveData<Facts> = MutableLiveData()

    /**
     * base url
     */
    private val baseUrl = "https://dl.dropboxusercontent.com"

    /**
     * Method to fetch the canada facts list from rest api.
     */
    suspend fun fetchCanadaFacts() {
        val fetchFactsListApi = getRetrofitInstance().create(FetchFactsListApi::class.java)
        val response = fetchFactsListApi.getFactsList()
        if (response.isSuccessful) {
            Log.i("info", "Facts Response ${response.body()}")
            if (response.isSuccessful) {
                apiResponseLD.value = response.body()
            }
        } else {
            Log.i("info", "facts request failed")
        }
    }

    /**
     * Method to get live data response of api request
     */
    fun getFactsLiveData(): MutableLiveData<Facts> {
        return apiResponseLD
    }

    /**
     * Method to get retrofit instance
     */
    private fun getRetrofitInstance(): Retrofit {
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
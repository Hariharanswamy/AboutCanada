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
     * Data received from web.
     */
    private var factsData: Facts? = null

    /**
     * livedata for response received in rest api.
     */
    private val apiResponseLD: MutableLiveData<Int> = MutableLiveData()

    /**
     * base url
     */
    private val baseUrl = "https://dl.dropboxusercontent.com/"

    /**
     * Method to fetch the canada facts list from rest api.
     */
    suspend fun fetchCanadaFacts() {
        val fetchFactsListApi = getRetrofitInstance().create(FetchFactsListApi::class.java)
        val response = fetchFactsListApi.getFactsList()
        Log.i("info", "Facts Response $response")
        apiResponseLD.value = response.code()
        if (response.isSuccessful) {
            factsData = response.body()
        }
    }

    /**
     * Method to get retrofit instance
     */
    private fun getRetrofitInstance(): Retrofit {
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
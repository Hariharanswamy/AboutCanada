package com.hariharan.aboutcanada.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hariharan.aboutcanada.data.model.Facts
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        val call = fetchFactsListApi.getFactsList()
        call.enqueue(object : Callback<Facts> {

            override fun onResponse(call: Call<Facts>, response: Response<Facts>) {
                Log.i("info", "Facts Response $response")
                apiResponseLD.value = response.code()
                if (response.isSuccessful) {
                    factsData = response.body()
                }
            }

            override fun onFailure(call: Call<Facts>, t: Throwable) {
                apiResponseLD.value = -1
                Log.e("app", "Error in network call ${t.message}")
            }
        })
    }

    /**
     * Method to get live data response of api request
     */
    fun getFactsLiveData(): MutableLiveData<Int> {
        return apiResponseLD
    }

    /**
     * Method to get response data from api
     */
    fun getResponseData() : Facts? {
        return factsData
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
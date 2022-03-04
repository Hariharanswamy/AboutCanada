package com.hariharan.aboutcanada.data

import com.hariharan.aboutcanada.data.model.Facts
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit interface to fetch json data from webservice.
 */
interface FetchFactsListApi {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    suspend fun getFactsList() : Response<Facts>
}
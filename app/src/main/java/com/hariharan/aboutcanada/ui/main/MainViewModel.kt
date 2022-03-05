package com.hariharan.aboutcanada.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hariharan.aboutcanada.data.DataRepository
import com.hariharan.aboutcanada.data.model.Facts
import kotlinx.coroutines.launch

/**
 * View model for main fragment.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DataRepository = DataRepository()

    private var data: MutableLiveData<Facts> = repository.getFactsLiveData()

    private var context = application.applicationContext

    init {
        fetchData()
    }

    fun getResponseCode(): MutableLiveData<Facts> {
        return data
    }

    /**
     * Method to fetch the data if internet is available
     */
    fun fetchData() {
        if (isNetworkAvailable()) {
            fetchDataCoroutine()
        }
    }

    /**
     * Method to check if internet is available.
     */
    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }

    /**
     * Method to fetch data using co-routine
     */
    private fun fetchDataCoroutine() {
        viewModelScope.launch {
            repository.fetchCanadaFacts()
        }
    }
}
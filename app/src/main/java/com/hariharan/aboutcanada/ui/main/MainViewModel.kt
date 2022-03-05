package com.hariharan.aboutcanada.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariharan.aboutcanada.data.DataRepository
import com.hariharan.aboutcanada.data.model.Facts
import kotlinx.coroutines.launch

/**
 * View model for main fragment.
 */
class MainViewModel : ViewModel() {

    private var repository: DataRepository = DataRepository()

    private var data: MutableLiveData<Facts> = repository.getFactsLiveData()

    fun getResponseCode(): MutableLiveData<Facts> {
        return data
    }

    fun fetchData() {
        viewModelScope.launch {
            repository.fetchCanadaFacts()
        }
    }
}
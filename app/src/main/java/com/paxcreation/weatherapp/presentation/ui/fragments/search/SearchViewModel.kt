package com.paxcreation.weatherapp.presentation.ui.fragments.search

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.domain.usecase.GetLocationByQuery
import com.paxcreation.weatherapp.presentation.extensions.onError
import com.paxcreation.weatherapp.presentation.extensions.onSuccess
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getLocationByQuery: GetLocationByQuery,
) : BaseViewModel() {
    private val listLocationSearch = ArrayList<LocationSearch>()
    private val TAG = "SearchViewModel"
    var locationSearchLiveData = MutableLiveData<List<LocationSearch>>()

    override fun onFirstTimeUiCreate(bundle: Bundle) {
        searchLocationByQuery("ho chi minh")
    }

    fun searchLocationByQuery(query: String) {
        listLocationSearch.clear()
        getLocationByQuery(query).execute(true, onSuccess {
            locationSearchLiveData.postValue(it)
        }, onError {
            Log.e("TAG", it.toString())
        })
    }

    fun clear() {
        listLocationSearch.clear()
    }
}
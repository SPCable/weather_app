package com.paxcreation.weatherapp.presentation.ui.fragments.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.FragmentSearchBinding
import com.paxcreation.weatherapp.presentation.extensions.createLoadingDialog
import com.paxcreation.weatherapp.presentation.extensions.showToast
import com.paxcreation.weatherapp.presentation.ui.adapter.ItemSearchNameAdapter
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModelRecyclerViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment :
    BaseViewModelRecyclerViewFragment<FragmentSearchBinding, SearchViewModel, ItemSearchNameAdapter>() {
    private val TAG = "SearchFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query.isEmpty()) {
                    viewModel.searchLocationByQuery(query)
                    true

                } else {
                    viewModel.searchLocationByQuery(query)
                    true
                }
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return if (newText.isEmpty()) {
                    viewModel.searchLocationByQuery(newText)
                    true

                } else {
                    viewModel.searchLocationByQuery(newText)
                    true
                }
            }
        })

        activity?.createLoadingDialog()
    }

    override fun doRefresh() {
    }


    override fun initObserver() {
        viewModel.locationSearchLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }


    override fun createViewModelClass() = SearchViewModel::class.java


    override fun getLayoutResource() = R.layout.fragment_search
}
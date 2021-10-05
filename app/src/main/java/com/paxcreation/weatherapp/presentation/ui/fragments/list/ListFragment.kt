package com.paxcreation.weatherapp.presentation.ui.fragments.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.FragmentListBinding
import com.paxcreation.weatherapp.presentation.extensions.showDialog
import com.paxcreation.weatherapp.presentation.extensions.showToast
import com.paxcreation.weatherapp.presentation.ui.adapter.LocationSreachAdapter
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModelRecyclerViewFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListFragment :
    BaseViewModelRecyclerViewFragment<FragmentListBinding, ListViewModel, LocationSreachAdapter>() {
    private val TAG = "ListFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun doRefresh() {
        this.setLoading(true)
    }

    override fun initObserver() {
        viewModel.locationSearchLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
        adapter.setOnClick { item, position ->
            activity?.showToast(item.toString() + position)
            context?.showDialog(
                "Remove?",
                "Are you sure you want to delete ${item.name}?",
                "Yes",
                {
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        viewModel.deleteItem(item.id)
                    }
                    activity?.showToast("Success")
                },
                "No", {}
            )
        }
    }


    override fun createViewModelClass() = ListViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_list
}
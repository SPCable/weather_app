package com.paxcreation.weatherapp.presentation.ui.base.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.presentation.ui.base.fragments.BaseViewModelFragment
import com.paxcreation.weatherapp.presentation.ui.widget.PreCachingLayoutManager
import javax.inject.Inject


abstract class BaseViewModelRecyclerViewFragment<B : ViewDataBinding,
        VM : BaseViewModel,
        A : RecyclerView.Adapter<*>> : BaseViewModelFragment<B, VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var adapter: A

    protected var isRefreshing: Boolean = false

    var recycleView: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    var layoutManager: RecyclerView.LayoutManager? = null

    override fun initialize(view: View, ctx: Context?) {
        recycleView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        if (hasStableIds()) {
            adapter.setHasStableIds(true)
        }
        layoutManager = createLayoutManager()
        recycleView?.let {
            it.layoutManager = layoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = adapter
            it.setHasFixedSize(hasFixedSize())
        }
        swipeRefreshLayout?.let {
            it.setOnRefreshListener(this)
            it.setColorSchemeResources(
                R.color.purple_700,
                R.color.purple_500,
                R.color.purple_200,
                R.color.teal_700
            )
        }
    }

    override fun onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true
            doRefresh()
        }
    }

    open fun doRefresh() {

    }

    open fun createLayoutManager(): RecyclerView.LayoutManager = PreCachingLayoutManager(activity)

    open fun hasFixedSize() = false

    open fun hasStableIds() = false

    override fun setLoading(loading: Boolean) {
        if (swipeRefreshLayout == null) {
            super.setLoading(loading)
        } else {
            if (!loading) {
                swipeRefreshLayout?.isRefreshing = false
                isRefreshing = false
            } else {
                swipeRefreshLayout?.isRefreshing = true
            }
        }
    }

    fun getRecyclerView(): RecyclerView? = recycleView
}

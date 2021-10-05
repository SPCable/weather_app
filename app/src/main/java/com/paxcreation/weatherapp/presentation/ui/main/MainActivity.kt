package com.paxcreation.weatherapp.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.ActivityMainBinding
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.presentation.adapter.ViewPagerAdapter
import com.paxcreation.weatherapp.presentation.extensions.showToast
import com.paxcreation.weatherapp.presentation.ui.base.activity.BaseViewModelActivity
import com.paxcreation.weatherapp.presentation.ui.fragments.home.HomeFragment
import com.paxcreation.weatherapp.presentation.ui.fragments.list.ListFragment
import com.paxcreation.weatherapp.presentation.ui.fragments.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var viewPaper: ViewPager
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val TAG = "MainActivity"
    private val defaultID = "1252431"
    private val listLocationSearch = ArrayList<LocationSearch>()

    override fun initObserver() {
        viewModel.listWeatherLiveData.observe(this, {

            listLocationSearch.clear()
            it.forEach { locationSearch ->
                listLocationSearch.add(locationSearch)
            }
            setUpViewPaper()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding.buttonMenu.setOnClickListener {
            viewModel.openSettingActivity(this)
        }

        setContentView(binding.root)
    }


    private fun setUpViewPaper() {
        Log.d(TAG, "1")
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(HomeFragment(defaultID), "Home")
        //add weather fragment by local + remote

        this.showToast(listLocationSearch.size.toString())
        listLocationSearch.forEach { locationSearch ->
            viewPagerAdapter.addFragment(
                HomeFragment(locationSearch.woeid.toString()),
                locationSearch.title
            )
        }

        //search + list (fragment)
        viewPagerAdapter.addFragment(SearchFragment(), "Search")
        viewPagerAdapter.addFragment(ListFragment(), "List")

        viewPaper = binding.viewPaper
        viewPaper.adapter = viewPagerAdapter
        binding.bottomNavigation.bottomNAV.setViewPager(viewPaper)
    }

    override fun createViewModelClass() = MainViewModel::class.java

    override fun getLayoutResource() = R.layout.activity_main
}
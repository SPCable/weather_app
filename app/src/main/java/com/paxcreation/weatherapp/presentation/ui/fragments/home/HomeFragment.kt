package com.paxcreation.weatherapp.presentation.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.FragmentHomeBinding
import com.paxcreation.weatherapp.domain.model.Location
import com.paxcreation.weatherapp.presentation.ui.adapter.LocationWeatherDayAdapter
import com.paxcreation.weatherapp.presentation.ui.base.fragments.BaseViewModelFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment(
    private val woid: String,
) :
    BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>() {
    private val TAG = "HomeFragment"
    private var temperature_unit = false

    @Inject
    lateinit var weatherDayAdapter: LocationWeatherDayAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setOnClickMenu {
            navigatorHelper.actionOpenSettingActivity(requireContext())
        }
    }

    override fun initObserver() {
        viewModel.loadLocationById(woid)
        viewModel.location.observe(viewLifecycleOwner,
            {
                setupAdapter(it)
                weatherDayAdapter.submitList(it.consolidated_weather)
            })
    }

    private fun setupAdapter(location: Location) {
        var dv = "°C"
        var temp = location.consolidated_weather[0].the_temp
        var tempMin = location.consolidated_weather[0].min_temp
        var tempMax = location.consolidated_weather[0].max_temp
        if (viewModel.checkTemperature()) {
            tempMax =
                viewModel.changeTemperatureCtoF(tempMax)
            tempMin =
                viewModel.changeTemperatureCtoF(tempMin)
            temp =
                viewModel.changeTemperatureCtoF(temp)
            dv = "°F"
        }
        binding.includeTop.textTempMin.text = tempMin.toInt().toString() + dv
        binding.includeTop.textTempBig.text = temp.toInt().toString() + dv
        binding.includeTop.textTempMax.text = tempMax.toInt().toString() + dv


        val key = location.consolidated_weather[0].weather_state_abbr
        Picasso.get().load(
            "https://www.metaweather.com/static/img/weather/png/$key.png"
        ).into(binding.includeTop.imageView6)
        binding.includeTop.textStatusWeather.text =
            location.consolidated_weather[0].weather_state_name
        binding.includeTop.textNameCity.text = location.title
        binding.includeTop.textCorrect.text =
            location.consolidated_weather[0].predictability.toString() + "%"

        binding.include.textSunrise.text = location.sun_rise.substring(11, 16) + "am"
        binding.include.textSunset.text = location.sun_set.substring(11, 16) + "pm"

        binding.include.textHumidity.text =
            location.consolidated_weather[0].humidity.toString() + "%"
        binding.include.textPressure.text =
            location.consolidated_weather[0].air_pressure.toInt().toString() + "mb"
        binding.include.textWind.text =
            location.consolidated_weather[0].wind_speed.toString().substring(0, 3) + "mph"
        binding.include.textVisibility.text =
            location.consolidated_weather[0].visibility.toString().substring(0, 3) + "miles"
    }


    override fun createViewModelClass() = HomeViewModel::class.java

    override fun initialize(view: View, ctx: Context?) {
        binding.recyclerViewMain.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = weatherDayAdapter
        }
    }

    override fun getLayoutResource() = R.layout.fragment_home
}
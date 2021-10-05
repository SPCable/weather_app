package com.paxcreation.weatherapp.presentation.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.LayoutBottomSheetBinding
import com.paxcreation.weatherapp.domain.model.Location
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.presentation.ui.adapter.LocationWeatherDayAdapter
import com.paxcreation.weatherapp.presentation.ui.base.dialog.BaseBottomSheetViewModelDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetFragment(private val woid: String) :
    BaseBottomSheetViewModelDialogFragment<LayoutBottomSheetBinding, BottomViewModel>() {
    private val TAG = "BottomSheet"

    @Inject
    lateinit var weatherDayAdapter: LocationWeatherDayAdapter
    override fun getLayoutResource() = R.layout.layout_bottom_sheet

    override fun createViewModelClass() = BottomViewModel::class.java

    override fun initialize(view: View, ctx: Context?) {
        viewModel.loadLocationById(woid)
        viewModel.location.observe(viewLifecycleOwner,
            {
                setupAdapter(it)
                setupClick(it)
                weatherDayAdapter.submitList(it.consolidated_weather)
            })

        binding.includeWeather.recyclerViewMain.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = weatherDayAdapter
        }
    }

    private fun setupClick(location: Location) {
        binding.setOnClickRight {
            this.dismiss()
        }
        binding.setOnClickLeft {
            val locationSearch = LocationSearch(
                location.title, location.location_type,
                location.latt_long, location.woeid
            )
            insertWeatherLocal(locationSearch)
            this.dismiss()
        }
    }

    private fun insertWeatherLocal(locationSearch: LocationSearch) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.insertWeatherLocal(locationSearch)
        }
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

        binding.includeWeather.includeTop.textTempMin.text = tempMin.toInt().toString() + dv
        binding.includeWeather.includeTop.textTempBig.text = temp.toInt().toString() + dv
        binding.includeWeather.includeTop.textTempMax.text = tempMax.toInt().toString() + dv


        val key = location.consolidated_weather[0].weather_state_abbr
        Picasso.get().load(
            "https://www.metaweather.com/static/img/weather/png/$key.png"
        ).into(binding.includeWeather.includeTop.imageView6)

        Picasso.get().load(
            "https://picsum.photos/2500/4000"
        ).resize(1712, 3364).into(binding.imageBackground)
        binding.includeWeather.includeTop.textStatusWeather.text =
            location.consolidated_weather[0].weather_state_name
        binding.includeWeather.includeTop.textNameCity.text = location.title
        binding.includeWeather.includeTop.textCorrect.text =
            location.consolidated_weather[0].predictability.toString() + "%"

        binding.includeWeather.include.textSunrise.text =
            location.sun_rise.substring(11, 16) + "am"
        binding.includeWeather.include.textSunset.text =
            location.sun_set.substring(11, 16) + "pm"

        binding.includeWeather.include.textHumidity.text =
            location.consolidated_weather[0].humidity.toString() + "%"
        binding.includeWeather.include.textPressure.text =
            location.consolidated_weather[0].air_pressure.toInt().toString() + "mb"
        binding.includeWeather.include.textWind.text =
            location.consolidated_weather[0].wind_speed.toString().substring(0, 3) + "mph"
        binding.includeWeather.include.textVisibility.text =
            location.consolidated_weather[0].visibility.toString().substring(0, 3) + "miles"


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeWeather.setOnClickMenu {
            Toast.makeText(context, "Demo", Toast.LENGTH_SHORT).show()
        }
        binding.setOnClickRight {
            Toast.makeText(context, "Demo", Toast.LENGTH_SHORT).show()
        }
        binding.setOnClickLeft {
            Toast.makeText(context, "Demo", Toast.LENGTH_SHORT).show()
        }
    }
}
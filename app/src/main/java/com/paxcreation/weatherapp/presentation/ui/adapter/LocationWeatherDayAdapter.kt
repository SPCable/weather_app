package com.paxcreation.weatherapp.presentation.ui.adapter

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.ItemWeatherListDayBinding
import com.paxcreation.weatherapp.domain.config.AppConfig
import com.paxcreation.weatherapp.domain.model.Location.ConsolidatedWeather
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.presentation.ui.base.adapter.BaseListAdapter
import com.paxcreation.weatherapp.presentation.ui.base.adapter.BaseViewHolder
import com.paxcreation.weatherapp.presentation.utils.Math
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ActivityContext
import java.time.LocalDate
import javax.inject.Inject

class LocationWeatherDayAdapter @Inject constructor(
    @ActivityContext context: Context,
) : BaseListAdapter<ConsolidatedWeather, ItemWeatherListDayBinding>(
    context,
    object : DiffUtil.ItemCallback<ConsolidatedWeather>() {
        override fun areItemsTheSame(
            oldItem: ConsolidatedWeather,
            newItem: ConsolidatedWeather
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ConsolidatedWeather,
            newItem: ConsolidatedWeather
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun itemLayoutResource() = R.layout.item_weather_list_day
    var onClickItem: ((item: LocationSearch, position: Int) -> Unit)? = null

    fun setOnClick(onClickItem: (item: LocationSearch, position: Int) -> Unit) {
        this.onClickItem = onClickItem
    }

    private inner class WeatherDayViewHolder(view: View) :
        BaseViewHolder<ItemWeatherListDayBinding>(view) {
        init {
        }
    }

    override fun createViewHolder(itemView: View): BaseViewHolder<ItemWeatherListDayBinding> =
        WeatherDayViewHolder(itemView)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        binding: ItemWeatherListDayBinding,
        dto: ConsolidatedWeather,
        position: Int
    ) {
        var dv = "ºC"
        var temp = dto.the_temp
        val sharedPrefsManager =
            context.getSharedPreferences(AppConfig.SHARE_PREF_NAME, Context.MODE_PRIVATE)
        val tempUnit = sharedPrefsManager.getInt("temperature", 0) == 1
        if (tempUnit) {
            dv = "ºF"
            temp = Math.changeTemperatureCtoF(dto.the_temp)
        }

        val date = dto.applicable_date.split("-")
        val day = LocalDate.of(date[0].toInt(), date[1].toInt(), date[2].toInt()).dayOfWeek
        val keyImageWeather = dto.weather_state_abbr

        Picasso.get()
            .load("https://www.metaweather.com/static/img/weather/png/$keyImageWeather.png")
            .into(binding.imageView9)

        binding.textDay.text = day.toString().uppercase().substring(0, 3)
        binding.textTempCorrect.text = dto.predictability.toString() + "%"
        binding.textTempDay.text = temp.toInt().toString() + dv
    }
}
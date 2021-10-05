package com.paxcreation.weatherapp.presentation.ui.adapter

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.ItemWeatherListBinding
import com.paxcreation.weatherapp.domain.config.AppConfig
import com.paxcreation.weatherapp.domain.model.WeatherSearch
import com.paxcreation.weatherapp.presentation.ui.base.adapter.BaseListAdapter
import com.paxcreation.weatherapp.presentation.ui.base.adapter.BaseViewHolder
import com.paxcreation.weatherapp.presentation.utils.Math
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ListWeatherAdapter @Inject constructor(
    @ActivityContext context: Context,
) : BaseListAdapter<WeatherSearch, ItemWeatherListBinding>(
    context,
    object : DiffUtil.ItemCallback<WeatherSearch>() {
        override fun areItemsTheSame(
            oldItem: WeatherSearch,
            newItem: WeatherSearch
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherSearch,
            newItem: WeatherSearch
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun itemLayoutResource() = R.layout.item_weather_list

    override fun createViewHolder(itemView: View): BaseViewHolder<ItemWeatherListBinding> =
        LocationSearchViewHolder(itemView)

    override fun onBindViewHolder(
        binding: ItemWeatherListBinding,
        dto: WeatherSearch,
        position: Int
    ) {
        var dv = "ºC"
        var temp = dto.temp
        val sharedPrefsManager =
            context.getSharedPreferences(AppConfig.SHARE_PREF_NAME, Context.MODE_PRIVATE)
        val tempUnit = sharedPrefsManager.getInt("temperature", 0) == 1
        if (tempUnit) {
            dv = "ºF"
            temp = Math.changeTemperatureCtoF(dto.temp)
        }
        binding.textTemp.text = temp.toInt().toString() + dv
        binding.textNameCity.text = dto.name
        val keyImageWeather = dto.status
        Picasso.get()
            .load("https://www.metaweather.com/static/img/weather/png/$keyImageWeather.png")
            .into(binding.imageWeather)
    }

    var onClickItem: ((item: WeatherSearch, position: Int) -> Unit)? = null

    fun setOnClick(onClickItem: (item: WeatherSearch, position: Int) -> Unit) {
        this.onClickItem = onClickItem
    }

    private inner class LocationSearchViewHolder(view: View) :
        BaseViewHolder<ItemWeatherListBinding>(view) {
        init {
            val item = getItem(bindingAdapterPosition)
            view.setOnClickListener {
                Toast.makeText(context, item.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
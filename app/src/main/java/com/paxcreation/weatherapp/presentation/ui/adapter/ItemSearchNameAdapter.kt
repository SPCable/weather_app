package com.paxcreation.weatherapp.presentation.ui.adapter

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.ItemWeatherSearchBinding
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.presentation.ui.base.adapter.BaseListAdapter
import com.paxcreation.weatherapp.presentation.ui.base.adapter.BaseViewHolder
import com.paxcreation.weatherapp.presentation.ui.dialog.BottomSheetFragment
import com.paxcreation.weatherapp.presentation.ui.main.MainActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ItemSearchNameAdapter @Inject constructor(
    @ActivityContext context: Context,
) : BaseListAdapter<LocationSearch, ItemWeatherSearchBinding>(
    context,
    object : DiffUtil.ItemCallback<LocationSearch>() {
        override fun areItemsTheSame(
            oldItem: LocationSearch,
            newItem: LocationSearch
        ): Boolean {
            return oldItem.woeid == newItem.woeid
        }

        override fun areContentsTheSame(
            oldItem: LocationSearch,
            newItem: LocationSearch
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun itemLayoutResource() = R.layout.item_weather_search

    override fun createViewHolder(itemView: View): BaseViewHolder<ItemWeatherSearchBinding> =
        ItemWeatherSearchViewHolder(itemView)

    override fun onBindViewHolder(
        binding: ItemWeatherSearchBinding,
        dto: LocationSearch,
        position: Int
    ) {
        binding.textNameCity.text = dto.title
    }

    var onClickItem: ((item: LocationSearch, position: Int) -> Unit)? = null

    fun setOnClick(onClickItem: (item: LocationSearch, position: Int) -> Unit) {
        this.onClickItem = onClickItem
    }

    private inner class ItemWeatherSearchViewHolder(view: View) :
        BaseViewHolder<ItemWeatherSearchBinding>(view) {
        init {
            val manager: FragmentManager =
                (context as MainActivity).supportFragmentManager
            binding.itemWeather.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                val bottomSheetFragment = BottomSheetFragment(item.woeid.toString())
                bottomSheetFragment.show(manager, bottomSheetFragment.tag)
            }
        }
    }
}
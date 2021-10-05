package com.paxcreation.weatherapp.presentation.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.paxcreation.weatherapp.domain.itemviewmodel.ItemViewModel
import com.paxcreation.weatherapp.presentation.extensions.genericCastOrNull
import dagger.hilt.android.qualifiers.ActivityContext

abstract class BaseItemListAdapter(
    @ActivityContext val context: Context,
    diffUtil: DiffUtil.ItemCallback<ItemViewModel>
) : ListAdapter<ItemViewModel, BaseItemViewHolder<ItemViewModel, ViewDataBinding>>(diffUtil) {

    override fun getItemViewType(position: Int): Int {
        return setItemViewType(getItemAt(position)!!)
    }

    override fun onBindViewHolder(
        holder: BaseItemViewHolder<ItemViewModel, ViewDataBinding>,
        position: Int
    ) {
        holder.setItem(getItemAt(position)!!, holder.binding)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseItemViewHolder<ItemViewModel, ViewDataBinding> = createItemViewHolder(
        LayoutInflater.from(context).inflate(getLayoutResource(viewType), parent, false), viewType
    )

    fun getItemAt(position: Int): ItemViewModel? = getItem(position)

    inline fun <reified T> getItemAt(position: Int): T? = genericCastOrNull(getItemAt(position))

    abstract fun setItemViewType(item: ItemViewModel): Int

    abstract fun getLayoutResource(viewType: Int): Int

    abstract fun createItemViewHolder(
        view: View,
        viewType: Int
    ): BaseItemViewHolder<ItemViewModel, ViewDataBinding>
}
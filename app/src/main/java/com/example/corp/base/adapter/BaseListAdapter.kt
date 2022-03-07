package com.example.corp.base.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : ListAdapterItem, VB : ViewBinding, VHolder : BaseViewHolder<T, VB>>
    (diffCallback: DiffUtil.ItemCallback<T> = ListAdapterItemDiffCallback()) : ListAdapter<T, VHolder>(diffCallback) {

    private val itemList: MutableList<T> = ArrayList()

    protected abstract fun bind(holder: VB, item: T, position: Int)

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        bind(holder.viewDataBinding, itemList[position], position)
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun replaceData(newList: List<T>?) {
        itemList.clear()
        itemList.addAll(newList ?: emptyList())
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun addData(newList: List<T>?) {
        itemList.addAll(newList ?: emptyList())
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size

    fun getData(): MutableList<T> {
        return itemList
    }
}

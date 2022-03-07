package com.example.corp.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

open class BaseListAdapter<T : ListAdapterItem, VB : ViewBinding, VHolder : BaseViewHolder<T, VB>>
    (diffCallback: DiffUtil.ItemCallback<T> = ListAdapterItemDiffCallback()) : ListAdapter<T, VHolder>(diffCallback) {


    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(holder.viewDataBinding, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        TODO("Not yet implemented")
    }
}

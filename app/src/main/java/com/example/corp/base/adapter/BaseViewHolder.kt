package com.example.corp.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<DataType, BindingType : ViewBinding> constructor(val viewDataBinding: BindingType) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    var item: DataType? = null

    abstract fun bind(binding: BindingType, item: DataType?)
}

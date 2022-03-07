package com.example.corp.ui.fragment

import com.example.corp.base.adapter.BaseViewHolder
import com.example.corp.common.Person
import com.example.corp.databinding.ItemPersonLayoutBinding


open class UserListViewHolder(userItemBinding: ItemPersonLayoutBinding) :
    BaseViewHolder<Person, ItemPersonLayoutBinding>(userItemBinding) {
    override fun bind(binding: ItemPersonLayoutBinding, item: Person?) {
        binding.root.text = item.toString()
    }
}

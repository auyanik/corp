package com.example.corp.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.corp.base.adapter.BaseListAdapter
import com.example.corp.common.Person
import com.example.corp.databinding.ItemPersonLayoutBinding

class UserListAdapter : BaseListAdapter<Person, ItemPersonLayoutBinding, UserListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserListViewHolder(
        ItemPersonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}

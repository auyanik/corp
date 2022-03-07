package com.example.corp.ui.fragment

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.corp.base.BaseFragment
import com.example.corp.databinding.FragmentUserListBinding
import com.example.corp.ui.fragment.mvi.UserListViewIntent
import com.example.corp.ui.fragment.mvi.UserListViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding, UserListViewModel>() {

    private lateinit var userListAdapter: UserListAdapter

    override fun onFragmentCreated() {
        userListAdapter = UserListAdapter()
        binding.userListRecyclerView.adapter = userListAdapter
        lifecycleScope.launch {
            viewModel.processViewIntent(UserListViewIntent.Initial)
        }

        lifecycleScope.launch {
            viewModel.state.collect {
                render(UserListViewState.fromState(it))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun render(userListViewState: UserListViewState) {
        when (userListViewState) {
            is UserListViewState.Content -> {
                setLoadingIndicator(loader = false)
                userListAdapter.submitList(userListViewState.personList)
                userListAdapter.notifyDataSetChanged()
            }
            is UserListViewState.Error -> {
                setLoadingIndicator(loader = false)
                binding.userListRecyclerView.isVisible = false
                binding.retryButton.isVisible = true
                binding.noHaveItemTextView.isVisible = true
            }
            UserListViewState.Loading -> setLoadingIndicator(loader = true)
        }
    }

    override val viewModel: UserListViewModel by viewModels()
    override fun getViewBinding() = FragmentUserListBinding.inflate(layoutInflater)
}

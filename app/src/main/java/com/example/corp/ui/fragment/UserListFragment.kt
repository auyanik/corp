package com.example.corp.ui.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.corp.base.BaseFragment
import com.example.corp.common.Person
import com.example.corp.common.ViewState
import com.example.corp.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding, UserListViewModel>() {

    private var userListAdapter = UserListAdapter()
    private var currentCount = 0
    private var isShowedLastUser = false

    override fun onFragmentCreated()  = with(binding){
        swipeRefreshLayout.setOnRefreshListener(refreshListener)
        userListAdapter.also { it.lastItemVisibleListener = lastItemVisibleListener }
        userListRecyclerView.apply {
            adapter = userListAdapter
        }
        retryButton.setOnClickListener {
            refresh()
        }
        callUsers()
    }

    override fun observe() {
        super.observe()
        lifecycleScope.launch {
            viewModel.userList.collect {
                binding.swipeRefreshLayout.isRefreshing = false
                manageLoadingDialog(it)
                setEmptyViewsVisibility(it, null)
                setAdapterValues(it)
            }
        }
    }

    private fun setAdapterValues(viewState: ViewState<List<Person>>) {
        val users = if (viewState is ViewState.Loaded) viewState.data else return
        setEmptyViewsVisibility(viewState, users.isEmpty())
        if (currentCount == 0) {
            userListAdapter.replaceData(users)
        } else {
            val newFlatList = (userListAdapter.getData() + users).map {
                it.id.toString() + it.fullName.replace(
                    " ",
                    ""
                ) to Person(it.id, it.fullName)
            }.toList().map {
                it.second
            }
            isShowedLastUser = users.isEmpty() && currentCount != 0
            userListAdapter.replaceData(newFlatList)
        }
    }

    private fun manageLoadingDialog(viewState: ViewState<List<Person>>) {
        setLoadingIndicator(loader = (viewState is ViewState.Loading))
    }

    private fun setEmptyViewsVisibility(viewState: ViewState<List<Person>>, isShowEmptyViews: Boolean?) = with(binding) {
        if (viewState is ViewState.Loading) return@with
        val state = (viewState is ViewState.Loaded) && (isShowEmptyViews!=null && isShowEmptyViews)
        noHaveItemTextView.isVisible = state
        retryButton.isVisible = state
        userListRecyclerView.isVisible = !state
    }

    private val lastItemVisibleListener: (position: Int) -> Unit = { pos ->
        currentCount = pos
        if (!isShowedLastUser) {
            callUsers()
        }
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swipeRefreshLayout.isRefreshing = true
        refresh()
    }

    private fun refresh() {
        currentCount = 0
        userListAdapter.replaceData(newList = listOf())
        isShowedLastUser = false
        callUsers()
    }

    private fun callUsers() {
        lifecycleScope.launch {
            viewModel.getPersons(currentCount)
        }
    }

    override val viewModel: UserListViewModel by viewModels()
    override fun getViewBinding() = FragmentUserListBinding.inflate(layoutInflater)
}

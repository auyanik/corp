package com.example.corp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.corp.ui.loader.LoadingDialog
import javax.inject.Inject

abstract class BaseFragment<BindingType : ViewBinding, ViewModelType : BaseViewModel> :
    Fragment() {
    private lateinit var loadingDialog: LoadingDialog
    lateinit var binding: BindingType
    protected abstract val viewModel: ViewModelType
    abstract fun getViewBinding(): BindingType
    abstract fun onFragmentCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        loadingDialog = LoadingDialog(requireContext())
        onFragmentCreated()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreated()
    }

    fun setLoadingIndicator(loader: Boolean) {
        loadingDialog.apply {
            if (loader) {
                if (!isShowing) show()
            } else if (isShowing) dismiss()
        }
    }
}


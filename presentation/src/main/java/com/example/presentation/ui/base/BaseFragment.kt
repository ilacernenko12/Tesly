package com.example.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.presentation.MainActivity
import com.example.presentation.R
import com.example.presentation.util.gone
import com.example.presentation.util.visible
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    abstract fun getErrorView(): View
    abstract fun getLoadingView(): View
    protected fun showError() {
        Snackbar.make(
            getErrorView(),
            getString(R.string.something_went_wrong),
            Snackbar.LENGTH_LONG
        ).show()
    }

    protected fun showLoading() {
        visible(getLoadingView())
        getRecyclerView()?.let { gone(it) }
    }

    abstract fun getRecyclerView(): View?

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        // чтобы во время сворачивания приложения
        // или в меню запущенных не были видны элементы активити
        (activity as? MainActivity)?.hideActivityElements()
    }

    // при возврате на активити восстановим видимость элементов
    override fun onStop() {
        super.onStop()
        (activity as? MainActivity)?.showActivityElements()
    }
}
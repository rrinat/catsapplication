package com.cats.catsapplication.core.mvvm

import android.arch.lifecycle.Observer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.cats.catsapplication.R
import com.cats.catsapplication.core.utils.gone
import com.cats.catsapplication.core.utils.makeViewFileIntent
import com.cats.catsapplication.core.utils.show

abstract class BaseFragment : Fragment() {

    protected abstract fun provideViewModel(): BaseViewModel
    protected abstract fun bindingUI()

    protected open fun getProgressView(): View? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        provideViewModel().getError().observe(this, Observer {
            showToast(it.orEmpty())
        })
        provideViewModel().getLoading().observe(this, Observer {
            if (it == true) {
                getProgressView()?.show()
            } else {
                getProgressView()?.gone()
            }
        })

        bindingUI()
    }

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    protected fun openShareFile(uri: Uri, mimeType: String) {
        val intent = requireContext().makeViewFileIntent(uri, mimeType)
        if (intent == null) {
            showToast(getString(R.string.intent_view_error))
        } else {
            requireActivity().startActivity(intent)
        }
    }
}
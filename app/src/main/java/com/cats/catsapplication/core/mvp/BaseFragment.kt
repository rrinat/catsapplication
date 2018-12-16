package com.cats.catsapplication.core.mvp

import android.net.Uri
import android.view.View
import android.widget.Toast
import com.cats.catsapplication.R
import com.cats.catsapplication.core.utils.gone
import com.cats.catsapplication.core.utils.makeViewFileIntent
import com.cats.catsapplication.core.utils.show

abstract class BaseFragment : MoxyFragment(), Presentable {

    protected open fun getProgressView(): View? = null

    override fun showProgress() {
        getProgressView()?.show()
    }

    override fun hideProgress() {
        getProgressView()?.gone()
    }

    override fun showError(error: String) {
        showToast(error)
    }

    override fun showToast(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }

    override fun openShareFile(uri: Uri, mimeType: String) {
        val intent = context?.makeViewFileIntent(uri, mimeType)
        if (intent == null) {
            showToast(getString(R.string.intent_view_error))
        } else {
            activity?.startActivity(intent)
        }
    }
}
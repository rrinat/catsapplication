package com.cats.catsapplication.core.mvp

import android.view.View
import android.widget.Toast
import com.cats.catsapplication.core.utils.gone
import com.cats.catsapplication.core.utils.show

abstract class BaseFragment : MoxyFragment(), Presentable {

    protected val progressView: View? = null
    protected val SCOPE_NAME = this.javaClass.name

    override fun showProgress() {
        progressView?.show()
    }

    override fun hideProgress() {
        progressView?.gone()
    }

    override fun hideError() {
        progressView?.gone()
    }

    override fun onReceiveError(message: String) {
        showToast(message)
    }

    protected fun showToast(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }
}
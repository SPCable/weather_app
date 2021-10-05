package com.paxcreation.weatherapp.presentation.ui.base.activity

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paxcreation.weatherapp.presentation.extensions.createLoadingDialog

abstract class BaseActivity : AppCompatActivity() {
    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!shouldUseDataBinding()) {
            setContentView(getLayoutResource())
        }
    }

    open fun setLoading(loading: Boolean) {
        if (loading) {
            hideLoading()
            if (progressDialog == null) {
                progressDialog = createLoadingDialog()
            } else {
                progressDialog = null
                progressDialog = createLoadingDialog()
            }
            progressDialog?.show()
        } else {
            hideLoading()
        }
    }

    fun hideLoading() {
        if (progressDialog != null && progressDialog?.isShowing == true)
            progressDialog?.dismiss()
    }


    abstract fun getLayoutResource(): Int


    protected open fun shouldUseDataBinding(): Boolean {
        return false
    }

    protected abstract fun getScreenName(): String?

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
        progressDialog = null
    }
}
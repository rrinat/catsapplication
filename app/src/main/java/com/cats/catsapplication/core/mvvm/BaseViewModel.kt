package com.cats.catsapplication.core.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.cats.catsapplication.core.utils.LifecycleProvider
import com.cats.catsapplication.core.utils.LifecycleTransformer

abstract class BaseViewModel : ViewModel() {

    private val provider = LifecycleProvider()
    protected val error = SingleLiveEvent<String>()
    protected val loading = MutableLiveData<Boolean>()

    protected fun <T> lifecycle(): LifecycleTransformer<T, T> = provider.lifecycle()

    fun getError(): LiveData<String> = error

    fun getLoading(): LiveData<Boolean> = loading

    override fun onCleared() {
        provider.unsubscribe()
    }
}
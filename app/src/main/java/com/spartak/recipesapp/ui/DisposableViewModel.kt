package com.spartak.recipesapp.ui

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class DisposableViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun <T : Any> Observable<T>.applySchedulers(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit,
    ) = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onNext, onError)
        .addToContainer()

    private fun Disposable.addToContainer() = disposable.add(this)

}


package com.spartak.recipesapp.ui

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class DisposableViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun <T : Any> Single<T>.applySchedulers(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit = {},
    ) = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onSuccess, onError)
        .addToContainer()

    fun <T : Any> Flowable<T>.applySchedulers(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = {},
        onComplete: () -> Unit = {},
    ) = this
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onNext,onError,onComplete)
        .addToContainer()

    fun Completable.applySchedulers(
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit = {},
    ) = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onComplete,onError)
        .addToContainer()

    private fun Disposable.addToContainer() = disposable.add(this)

}


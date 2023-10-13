package com.spartak.recipesapp.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


fun <T : Any> Observable<T>.applySchedulers(
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
): Disposable =
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(onNext, onError)

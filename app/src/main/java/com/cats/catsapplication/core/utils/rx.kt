package com.cats.catsapplication.core.utils

import android.os.Handler
import android.os.Looper
import com.cats.catsapplication.core.mvp.LoadingView
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.reactivestreams.Publisher


class LifecycleProvider {
    private var disposable: Disposable? = null
    private var observable: BehaviorSubject<Any>? = null

    fun unsubscribe() {
        if (disposable?.isDisposed?.not() == true) {
            observable?.onNext(Any())
            disposable?.dispose()
        }
        disposable = null
        observable = null
    }

    fun <T> lifecycle(): LifecycleTransformer<T, T> {
        if (observable == null || disposable == null) {
            observable = BehaviorSubject.create()
            disposable = observable!!.subscribe {}
        }
        return LifecycleTransformer(observable!!)
    }
}

class LifecycleTransformer<T : R, R>(private val lifecycle: Observable<Any>) : ObservableTransformer<T, R>,
    MaybeTransformer<T, R>,
    FlowableTransformer<T, R>, CompletableTransformer, SingleTransformer<T, R> {
    override fun apply(upstream: Single<T>): SingleSource<R> {
        return upstream.takeUntil(lifecycle.firstOrError()).map { it }
    }

    override fun apply(upstream: Completable): CompletableSource {
        return upstream.ambWith(lifecycle.concatMapCompletable { Completable.never() })
    }

    override fun apply(upstream: Flowable<T>): Publisher<R> = upstream.takeUntil(lifecycle.toFlowable(
        BackpressureStrategy.LATEST)).map { it }

    override fun apply(upstream: Maybe<T>): MaybeSource<R> = upstream.takeUntil(lifecycle.firstElement()).map { it }

    override fun apply(upstream: Observable<T>): ObservableSource<R> = upstream.takeUntil(lifecycle).map { it }
}

fun <T> Single<T>.async(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


object RxDecor {

    @JvmStatic
    fun <T> loadingSingle(view: LoadingView): SingleTransformer<T, T> = LoadingViewTransformer(view)

    private class LoadingViewTransformer<T : R, R>(private val loadingView: LoadingView) : SingleTransformer<T, R> {

        private val handler = Handler(Looper.getMainLooper())

        override fun apply(upstream: Single<T>): SingleSource<R> {
            return upstream
                .doOnSubscribe { handler.post { loadingView.showProgress() } }
                .doAfterTerminate { handler.post { loadingView.hideProgress() } }
                .doOnDispose { handler.post { loadingView.hideProgress() } }
                .map { it }
        }
    }
}

fun loadingView(show: (() -> (Unit)), hide: (() -> (Unit))): LoadingView = object : LoadingView {
    override fun hideProgress() = hide()
    override fun showProgress() = show()
}
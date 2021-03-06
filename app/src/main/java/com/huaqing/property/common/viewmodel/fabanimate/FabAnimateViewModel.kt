package com.huaqing.property.common.viewmodel.fabanimate

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import arrow.core.left
import arrow.core.right
import com.huaqing.property.base.viewmodel.BaseViewModel
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.model.Errors
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject

class FabAnimateViewModel : BaseViewModel() {

    private val scrollStateSubject: PublishSubject<Int> = PublishSubject.create()

    val visibleState: MutableLiveData<Boolean> = MutableLiveData()

    val stateChangesConsumer: (Int) -> Unit = {
        scrollStateSubject.onNext(it)
    }

    init {
        scrollStateSubject
            .map { it == RecyclerView.SCROLL_STATE_IDLE }
            .compose { upstream ->
                upstream.zipWith(upstream.startWith(true)) { last, current ->
                    when (last == current) {
                        true -> Errors.EmptyInputError.left()
                        false -> current.right()
                    }
                }
            }
            .flatMap { changed ->
                changed.fold({
                    Observable.empty<Boolean>()
                }, {
                    Observable.just(it)
                })
            }
            .observeOn(RxSchedulers.ui)
            .autoDisposable(this)
            .subscribe {
                applyState(visible = it)
            }
    }

    private fun applyState(visible: Boolean) {
        visibleState.postValue(visible)
    }

    companion object {
        fun instance() = FabAnimateViewModel()
    }
}
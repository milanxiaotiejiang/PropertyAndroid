package com.huaqing.property.base.viewmodel

import androidx.lifecycle.ViewModel
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class AutoDisposeViewModel : ViewModel(),
    LifecycleScopeProvider<AutoDisposeViewModel.ViewModelEvent> {

    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)

    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ViewModelEvent? {
        return lifecycleEvents.value
    }

    override fun onCleared() {
        lifecycleEvents.onNext(ViewModelEvent.CLEARED)
        super.onCleared()
    }

    companion object {
        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ViewModelEvent> { event ->
            when (event) {
                ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                else -> throw LifecycleEndedException(
                    "Cannot bind to ViewModel lifecycle after onCleared."
                )
            }
        }
    }
}
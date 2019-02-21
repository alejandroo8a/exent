package com.example.alien.excent.ui.core.home

import android.arch.lifecycle.ViewModel
import com.example.alien.excent.data.ResultData
import com.example.alien.excent.data.core.CoreRepository
import com.example.alien.excent.ui.util.NoOpDisposable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class HomeViewModel @Inject
internal constructor(
    private val coreRepository: CoreRepository,
    private val uiHomeMapper: UiHomeMapper
) : ViewModel() {

    private val eventsSubject = BehaviorSubject.create<ResultData<List<UiEvents>>>()
    private var eventsDisposable: Disposable = NoOpDisposable()

    fun eventContentUpdates(): Observable<ResultData<List<UiEvents>>> {
        return eventsSubject
    }

    fun updateEventsContent(type: EventType) {
        val idCategory = when(type){
            EventType.NEXT -> 1
            EventType.SPORTS -> 2
            EventType.CONCERTS -> 3
            EventType.FESTIVAL -> 4
            EventType.OTHERS -> 5
        }
        eventsDisposable = coreRepository.getEvents(0, idCategory)
            .map(uiHomeMapper::toUiEvent)
            .subscribe(eventsSubject::onNext)
    }

    fun searchEvents(event: String) {
        eventsDisposable = coreRepository.searchEvents(0, event)
            .map(uiHomeMapper::toUiEvent)
            .subscribe(eventsSubject::onNext)
    }

    override fun onCleared() {
        eventsDisposable.dispose()
    }

}
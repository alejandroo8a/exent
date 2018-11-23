package com.example.alien.excent.ui.core.home

import android.arch.lifecycle.ViewModel
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

    private val eventsSubject = BehaviorSubject.create<List<UiEvents>>()
    private var eventsDisposable: Disposable = NoOpDisposable()

    fun eventContentUpdates(): Observable<List<UiEvents>> {
        return eventsSubject
    }

    fun updateEventsContent(type: EventType) {
        val idCategory = when(type){
            EventType.NEXT -> 1
            EventType.CONCERTS -> 2
            EventType.FESTIVAL -> 3
            EventType.SPORTS -> 4
        }
        eventsDisposable = coreRepository.getEvents(0, 0, idCategory)
            .map(uiHomeMapper::toUiEvent)
            .subscribe(eventsSubject::onNext)
    }

    override fun onCleared() {
        eventsDisposable.dispose()
    }

}
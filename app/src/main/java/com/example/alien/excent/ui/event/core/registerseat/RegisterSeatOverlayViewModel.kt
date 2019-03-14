package com.example.alien.excent.ui.event.core.registerseat

import android.content.Context
import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.data.event.EventRepository
import com.example.alien.excent.ui.overlay.BaseOverlayViewModel
import com.example.alien.excent.ui.util.NoOpDisposable
import com.example.alien.excent.ui.util.overlay.ScreenShotUtil
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class RegisterSeatOverlayViewModel@Inject
internal constructor(
    context: Context,
    screenShotUtil: ScreenShotUtil,
    private val eventRepository: EventRepository
) : BaseOverlayViewModel(context, screenShotUtil) {

    private val sendSeatsSubject = PublishSubject.create<NetworkResult>()
    private var sendSeatsDisposable: Disposable = NoOpDisposable()

    fun sendSeatsesult(): Observable<NetworkResult> {
        return sendSeatsSubject
    }

    fun sendSeats(idEvent: Int, seats: ArrayList<String>) {
        sendSeatsDisposable.dispose()
        sendSeatsDisposable = eventRepository.sendSeats(idEvent, seats)
            .doOnError { error ->
                Timber.w(
                    "Error changing password: %s",
                    error.message
                )
            }
            .subscribe(sendSeatsSubject::onNext)
    }
}
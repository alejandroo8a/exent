package com.example.alien.excent.ui.event.core

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.core.home.UiEvents
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.example.alien.excent.ui.util.ThirdPartyIntentLauncher
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout
import kotlinx.android.synthetic.main.fragment_event_core.*
import org.jetbrains.anko.toast
import javax.inject.Inject

@Layout(R.layout.fragment_event_core)
class EventCoreFragment : ViewModelFragment<EventCoreViewModel>() {

    companion object {

        private const val event = "EVENT"
        private const val idEventName = "ID_EVENT"

        fun newInstance(uiEvent : UiEvents): EventCoreFragment{
            val fragment = EventCoreFragment()
            val args = Bundle()
            args.putSerializable(event, uiEvent)
            fragment.arguments = args
            return fragment
        }
    }

    @Callback
    lateinit var  navigation: Navigation
    var uiEvent: UiEvents? = null

    @Inject
    lateinit var thirdPartyIntentLauncher: ThirdPartyIntentLauncher

    override fun viewModelClass() = EventCoreViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEvent()
        loadData()

    }

    private fun getEvent() {
        uiEvent = arguments?.getSerializable(event) as UiEvents
    }

    private fun loadData() {
        uiEvent?.let {
            header.setTitle(it.title)
            txt_date.text = it.date
            txt_place.text = it.location
            txt_description_body.text = it.description
            Glide.with(context!!).load(it.image).apply(RequestOptions().error(ContextCompat.getDrawable(context!!, R.drawable.ic_broken_image))).into(im_background)
        }
    }

    @OnClick(R.id.et_buy_ticket)
    fun goToBuyTicket() {
        navigation.navigateToAction(UiAction.BUY_TICKET, uiEvent, event)
    }

    @OnClick(R.id.et_food)
    fun goToFood() {
        navigation.navigateToAction(UiAction.BUY_FOOD, uiEvent, event)
    }

    @OnClick(R.id.et_register)
    fun goToRegister() {
        navigation.navigateToAction(UiAction.REGISTER_SEAT, uiEvent?.idEvent, idEventName)
    }

    @OnClick(R.id.et_location)
    fun goToLocation() {
        uiEvent?.let { innerUiEvent ->
            view?.let { innerView ->
                thirdPartyIntentLauncher.launchMap(innerUiEvent.location, innerView)
            }
        }
    }

    @OnClick(R.id.et_news)
    fun goToNews() {
        navigation.navigateToAction(UiAction.NEWS, uiEvent, event)
    }

    @OnClick(R.id.et_comments)
    fun goToComments() {
        navigation.navigateToAction(UiAction.COMMENTS, uiEvent, event)
    }

    @OnClick(R.id.im_back)
    fun goToBack() {
        navigation.navigateBack()
    }
}

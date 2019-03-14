package com.example.alien.excent.ui.event.core.registerseat

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.example.alien.excent.R
import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.network.LoadingDialogFragment
import com.example.alien.excent.ui.overlay.BaseOverlayActivity
import com.metova.slim.annotation.Layout
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register_seat_overlay.*

@Layout(R.layout.activity_register_seat_overlay)
class RegisterSeatOverlayActivity : BaseOverlayActivity<RegisterSeatOverlayViewModel>() {

    private val idEventName = "ID_EVENT"

    @VisibleForTesting
    var loadingDialog = LoadingDialogFragment()

    @BindView(R.id.register_seat_root_view) lateinit var rootView: ConstraintLayout

    private lateinit var adapter: AdapterRegisterSeat

    override fun getRootViewForBlurredBackground(): View {
        return rootView
    }

    override fun viewModelClass() = RegisterSeatOverlayViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(viewModel().sendSeatsesult()
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { result -> loadingDialog.loadFinishedObservable(result) }
            .subscribe(this::handleForgotPasswordResult))
    }

    private fun handleForgotPasswordResult(result : NetworkResult) {
        when (result) {
            NetworkResult.SUCCESS -> {
                snackbarUtil.showSnackbar(rootView, R.string.event_seats_success)
                navigateBack()
            }
            NetworkResult.FORBIDDEN_ERROR -> snackbarUtil.showSnackbar(rootView, R.string.forbidden)
            NetworkResult.CONNECTION_ERROR -> snackbarUtil.showSnackbar(rootView, R.string.connection_error)
            else -> snackbarUtil.showSnackbar(rootView, R.string.generic_request_error)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureAdapter()
    }

    private fun configureAdapter() {
        rv_seats.layoutManager = LinearLayoutManager(applicationContext)
        adapter = AdapterRegisterSeat(applicationContext)
        rv_seats.adapter = adapter
    }

    @OnClick(R.id.im_up_amount_seat)
    fun addSeat() {
        if(txt_amount.text.toString().toInt() < 10) {
            adapter.addSeat()
            txt_amount.text = "${txt_amount.text.toString().toInt().inc()}"
        }
    }

    @OnClick(R.id.im_down_amount_seat)
    fun removeSeat() {
        if(txt_amount.text.toString().toInt() != 1) {
            adapter.removeSeat()
            txt_amount.text = "${txt_amount.text.toString().toInt().dec()}"
        }
    }

    @OnClick(R.id.btn_continue)
    fun addSeats() {
        val result = adapter.getDataOfList()
        val seats = ArrayList<String>()
        for(key in result.keys) {
            seats.add(result[key]?.text.toString())
        }
        loadingDialog.show(fragmentManager, null)
        viewModel().sendSeats(intent.extras.getInt(idEventName), seats)
    }

    @OnClick(R.id.im_close)
    fun goToBack() {
        navigateBack()
    }

    public override fun onStop() {
        loadingDialog.dismissAllowingStateLoss()
        super.onStop()
    }
}
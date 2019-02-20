package com.example.alien.excent.ui.settings.paymethods.addpaymentmethod

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import butterknife.OnClick
import butterknife.OnEditorAction
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.util.Keyboard
import com.example.alien.excent.ui.util.MaskWatcher
import com.example.alien.excent.ui.util.SnackbarUtil
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout
import kotlinx.android.synthetic.main.fragment_add_payment_method.*
import javax.inject.Inject

@Layout(R.layout.fragment_add_payment_method)
class AddPaymentMethodFragment : ViewModelFragment<AddPaymentMethodViewModel>() {

    @Inject
    lateinit var snackbarUtil: SnackbarUtil

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = AddPaymentMethodViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    @OnClick(R.id.im_back)
    fun goToBack() {
        navigation.navigateBack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMaskToView()
    }

    private fun setMaskToView() {
        edt_card.addTextChangedListener(MaskWatcher.setMaskCreditCard())
        edt_date.addTextChangedListener(MaskWatcher.setMaskDate())
    }

    @OnEditorAction(R.id.edt_cvc)
    fun onKeyboardAction(textView: TextView, actionId: Int): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            Keyboard.close(requireActivity(), view!!)
            saveCreditCard()
            true
        } else {
            false
        }
    }

    @OnClick(R.id.btn_save_credit_card)
    fun saveCreditCard() {
        snackbarUtil.showSnackbar(view!!, "Guardar tarjeta")
    }

    @OnClick(R.id.txt_question)
    fun openExplanation() {
        snackbarUtil.showSnackbar(view!!, "Mostrar puntos de seguridad")
    }
}

package com.example.alien.excent.ui.core.home


import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.util.SnackbarUtil
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout
import javax.inject.Inject

@Layout(R.layout.fragment_home)
class HomeFragment : ViewModelFragment<HomeViewModel>() {

    @Inject
    lateinit var snackbarUtil: SnackbarUtil

    @Callback
    lateinit var  navigation: Navigation

    override fun viewModelClass() = HomeViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

}

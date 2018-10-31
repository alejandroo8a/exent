package com.example.alien.excent.ui.core.home

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.util.SnackbarUtil
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout
import javax.inject.Inject
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import butterknife.BindView
import kotlinx.android.synthetic.main.fragment_home.*

@Layout(R.layout.fragment_home)
class HomeFragment : ViewModelFragment<HomeViewModel>(), TabLayout.OnTabSelectedListener {

    @Inject
    lateinit var snackbarUtil: SnackbarUtil

    @BindView(R.id.toolbar_home) lateinit var toolbarHome: Toolbar

    @Callback
    lateinit var  navigation: Navigation

    override fun viewModelClass() = HomeViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tl_home.addOnTabSelectedListener(this)
        configureToolbar()
        populateToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_ongoing_event -> snackbarUtil.showSnackbar(view!!, R.string.delete_ongoing_event)
            R.id.menu_pre_sell -> snackbarUtil.showSnackbar(view!!, R.string.delete_pre_sell)
            R.id.menu_good_deal -> snackbarUtil.showSnackbar(view!!, R.string.delete_good_deal)
            R.id.menu_re_sell -> snackbarUtil.showSnackbar(view!!, R.string.delete_re_sell)
            R.id.menu_food -> snackbarUtil.showSnackbar(view!!, R.string.menu_food)
            R.id.menu_settings -> snackbarUtil.showSnackbar(view!!, R.string.menu_settings)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbarHome)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    private fun populateToolbar() {
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_next_events)))
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_sports)))
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_concert)))
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_fests)))
    }


    override fun onTabReselected(tab: TabLayout.Tab?) {
        // no-op
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        // no-op
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        // no-op
    }
}
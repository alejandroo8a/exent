package com.example.alien.excent.ui.core.home

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import com.example.alien.excent.R
import com.example.alien.excent.data.ResultData
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.example.alien.excent.ui.util.SnackbarUtil
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject
import com.example.alien.excent.data.NetworkResult


@Layout(R.layout.fragment_home)
class HomeFragment : ViewModelFragment<HomeViewModel>(), TabLayout.OnTabSelectedListener {

    @Inject
    lateinit var snackbarUtil: SnackbarUtil

    @BindView(R.id.toolbar_home) lateinit var toolbarHome: Toolbar
    @BindView(R.id.rv_events) lateinit var rvEvents: RecyclerView
    lateinit var  type: EventType

    @Callback
    lateinit var  navigation: Navigation

    private val event = "EVENT"

    private lateinit var adapter: AdapterEvents
    private val totalColumns = 2

    override fun viewModelClass() = HomeViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tl_home.addOnTabSelectedListener(this)
        configureToolbar()
        populateToolbar()
        configureAdapter()
        rvEvents.requestFocus()
    }

    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(viewModel()?.eventContentUpdates()!!
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleEvents))
    }

    private fun handleEvents(result: ResultData<List<UiEvents>>) {
        when (result.networkResult) {
            NetworkResult.SUCCESS -> {
                if (result.data!!.isEmpty()) {
                    txt_no_info.visibility = View.VISIBLE
                    rvEvents.visibility = View.GONE
                } else {
                    txt_no_info.visibility = View.GONE
                    rvEvents.visibility = View.VISIBLE
                    adapter.populateEvents(result.data)
                }
            }
            NetworkResult.CONNECTION_ERROR -> {
                txt_no_info.visibility = View.GONE
                rvEvents.visibility = View.GONE
                snackbarUtil.showSnackbar(view!!, R.string.connection_error)
            }
            NetworkResult.AUTHORIZATION_ERROR, NetworkResult.FORBIDDEN_ERROR -> {
                txt_no_info.visibility = View.GONE
                rvEvents.visibility = View.GONE
                snackbarUtil.showSnackbar(view!!, R.string.forbidden)
            }
            else -> {
                txt_no_info.visibility = View.GONE
                rvEvents.visibility = View.GONE
                snackbarUtil.showSnackbar(view!!, R.string.generic_request_error)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_ongoing_event -> snackbarUtil.showSnackbar(view!!, R.string.delete_ongoing_event)
            R.id.menu_pre_sell -> navigation.navigateToAction(UiAction.TYPE_EVENT)
            R.id.menu_good_deal -> navigation.navigateToAction(UiAction.TYPE_EVENT)
            R.id.menu_re_sell -> navigation.navigateToAction(UiAction.TYPE_EVENT)
            R.id.menu_settings -> navigation.navigateToAction(UiAction.SETTINGS)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbarHome)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    private fun populateToolbar() {
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_next_events)).setTag(EventType.NEXT))
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_sports)).setTag(EventType.SPORTS))
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_concert)).setTag(EventType.CONCERTS))
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_fests)).setTag(EventType.FESTIVAL))
        tl_home.addTab(tl_home.newTab().setText(getString(R.string.home_others)).setTag(EventType.OTHERS))
    }

    private fun configureAdapter() {
        rv_events.setHasFixedSize(true)
        rv_events.layoutManager = GridLayoutManager(context, totalColumns)
        adapter = AdapterEvents(context!!, {
            navigateToEventDetail(it)
        })
        rv_events.adapter = adapter
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        // no-op
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        // no-op
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        this.type = tab.tag as EventType
        viewModel()?.updateEventsContent(type)
    }

    fun navigateToEventDetail(uiEvent : UiEvents){
        navigation.navigateToAction(UiAction.EVENT, uiEvent, event)
    }
}

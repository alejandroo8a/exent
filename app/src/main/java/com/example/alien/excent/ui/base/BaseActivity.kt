package com.example.alien.excent.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.metova.slim.Slim


abstract class BaseActivity : AppCompatActivity() {

    private lateinit var unbinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = Slim.createLayout(this, this)
        if (layout != null) {
            setContentView(layout)
        }
        unbinder = ButterKnife.bind(this)
        Slim.injectExtras(intent.extras, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }
}
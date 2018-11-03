package com.example.alien.excent.ui.settings

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.alien.excent.R

class SettingsHeader : ConstraintLayout {

    @BindView(R.id.txt_title) lateinit var txtTitle : TextView
    private lateinit var title : String

    constructor(context: Context?) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {

        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SettingsHeader)
        try {
            title = styledAttributes.getString(R.styleable.SettingsHeader_title)
        } finally {
            styledAttributes.recycle()
        }
        inflate(context, R.layout.settings_header, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)

        txtTitle.text = this.title
    }
}
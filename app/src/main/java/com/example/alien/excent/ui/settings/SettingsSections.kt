package com.example.alien.excent.ui.settings

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.example.alien.excent.R
import butterknife.ButterKnife
import com.bumptech.glide.Glide

class SettingsSections : ConstraintLayout {

    @BindView(R.id.txt_value) lateinit var txt_title : TextView
    @BindView(R.id.im_value) lateinit var im_value : ImageView
    private lateinit var title : String
    private lateinit var drawable : Drawable
    private lateinit var innerContext : Context

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs : AttributeSet){
        this.innerContext = context
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SettingsSections)
        try {
            title = styledAttributes.getString(R.styleable.SettingsSections_title)
            drawable = styledAttributes.getDrawable(R.styleable.SettingsSections_image)
        } finally {
            styledAttributes.recycle()
        }
        inflate(context, R.layout.settings_section, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)

        txt_title.text = this.title
        Glide.with(this.innerContext).load(this.drawable).into(this.im_value)
    }
}
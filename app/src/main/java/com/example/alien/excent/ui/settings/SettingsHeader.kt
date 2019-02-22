package com.example.alien.excent.ui.settings

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.alien.excent.R

class SettingsHeader : ConstraintLayout {

    @BindView(R.id.txt_title) lateinit var txtTitle : TextView
    @BindView(R.id.im_settings) lateinit var imSettings : ImageView
    private lateinit var title : String
    private var drawable : Drawable? = null
    private lateinit var innerContext : Context

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
        this.innerContext = context
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SettingsHeader)
        try {
            title = styledAttributes.getString(R.styleable.SettingsHeader_title)
            if(styledAttributes.getDrawable(R.styleable.SettingsHeader_image) != null) {
                drawable = styledAttributes.getDrawable(R.styleable.SettingsHeader_image)
            }
        } finally {
            styledAttributes.recycle()
        }
        inflate(context, R.layout.settings_header, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)

        txtTitle.text = this.title
        if(this.drawable != null) {
            Glide.with(this.innerContext).load(this.drawable).into(imSettings)
        }
    }

    fun setTitle(titleOut: String) {
        txtTitle.text = titleOut
    }

    fun setImage(imageOut: Int) {
        Glide.with(this.innerContext).load(ContextCompat.getDrawable(this.context, imageOut)).into(imSettings)
    }
}
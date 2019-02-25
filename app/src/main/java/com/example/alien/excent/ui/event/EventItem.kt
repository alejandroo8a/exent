package com.example.alien.excent.ui.event

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.alien.excent.R

class EventItem : ConstraintLayout {

    @BindView(R.id.txt_name_item_event) lateinit var txtName : TextView
    @BindView(R.id.im_background) lateinit var imBackground : ImageView
    private var title : String? = null
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
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.EventItem)
        try {
            title = styledAttributes?.getString(R.styleable.EventItem_title)
            drawable = styledAttributes?.getDrawable(R.styleable.EventItem_image)

        } finally {
            styledAttributes.recycle()
        }
        inflate(context, R.layout.event_item, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)

        this.title?.let {
            txtName.text = it
        }
        this.drawable?.let {
            Glide.with(this.innerContext).load(it).into(imBackground)
        }
    }
}
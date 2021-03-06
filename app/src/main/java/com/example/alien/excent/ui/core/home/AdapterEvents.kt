package com.example.alien.excent.ui.core.home

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alien.excent.R
import kotlinx.android.synthetic.main.item_home.view.*

class AdapterEvents(
    val context: Context,
    private val listener: (UiEvents) -> Unit,
    var aEvents: List<UiEvents> = ArrayList()
) : RecyclerView.Adapter<AdapterEvents.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(aEvents[position], listener)
    }

    override fun getItemCount(): Int = aEvents.size

    fun populateEvents(aEvents: List<UiEvents>) {
        this.aEvents = aEvents
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(event: UiEvents, listener: (UiEvents) -> Unit) = with(itemView) {
            Glide.with(context).load(event.image).apply(RequestOptions().error(ContextCompat.getDrawable(context, R.drawable.ic_broken_image))).into(im_background)
            txt_title.text = event.title
            txt_date.text = event.date
            txt_time.text = event.time
            txt_location.text = event.location
            txt_cost.text = event.price
            setOnClickListener { listener(event) }
        }
    }
}
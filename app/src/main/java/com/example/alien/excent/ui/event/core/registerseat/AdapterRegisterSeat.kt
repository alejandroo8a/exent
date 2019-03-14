package com.example.alien.excent.ui.event.core.registerseat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.alien.excent.R
import kotlinx.android.synthetic.main.item_register_seat.view.*

class AdapterRegisterSeat(
    val context: Context
) : RecyclerView.Adapter<AdapterRegisterSeat.ViewHolder>() {

    private var totalSeats = 1
    private val editTextList = HashMap<Int, EditText>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_register_seat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        editTextList[position] = (holder.valueEdit)
    }

    override fun getItemCount(): Int = totalSeats

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addSeat() {
        totalSeats = totalSeats.inc()
        notifyDataSetChanged()
    }

    fun removeSeat() {
        totalSeats = totalSeats.dec()
        editTextList.remove(totalSeats)
        notifyDataSetChanged()
    }

    fun getDataOfList() = editTextList

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val valueEdit: EditText = view.edt_register_seat

    }

}
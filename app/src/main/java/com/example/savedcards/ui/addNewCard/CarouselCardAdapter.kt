package com.example.savedcards.ui.addNewCard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savedcards.R

class CarouselCardAdapter(private val carouselDataList: ArrayList<String>) :
    RecyclerView.Adapter<CarouselCardAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.textview)
        val bgView = holder.itemView.findViewById<View>(R.id.view_bg)
        when (position){
            0 -> bgView.setBackgroundResource(R.drawable.card_bg_one)
            1 -> bgView.setBackgroundResource(R.drawable.card_bg_two)
            2 -> bgView.setBackgroundResource(R.drawable.card_bg_three)
            3 -> bgView.setBackgroundResource(R.drawable.card_bg_four)
            else -> bgView.setBackgroundResource(R.drawable.card_bg_five)
        }
        textView.text = carouselDataList[position]
    }


    override fun getItemCount(): Int {
        return carouselDataList.size
    }
}

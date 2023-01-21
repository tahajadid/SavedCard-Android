package com.example.savedcards.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.savedcards.MainActivity
import com.example.savedcards.R
import com.example.savedcards.data.CardInfo
import com.example.savedcards.util.numberCardUtil.ScratchCardUtil

class ListCardAdapter(
    private val context: Context?,
    private var listOfCards: ArrayList<CardInfo>
) : RecyclerView.Adapter<ListCardAdapter.ViewHolder>() {

    /**
     * Find all the views of the list item
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var number: TextView
        lateinit var name: TextView
        lateinit var date: TextView
        lateinit var previewView: ConstraintLayout

        /**
         * Show the data in the views
         */
        fun bindView(item: CardInfo, position: Int) {
            number = itemView.findViewById(R.id.card_number)
            name = itemView.findViewById(R.id.card_name)
            date = itemView.findViewById(R.id.card_date)
            previewView = itemView.findViewById(R.id.preview_view)

            number.text = ScratchCardUtil.addSeparatorToNumber(item.number.toString())
            name.text = item.fullName
            date.text = item.expirationMonth +
                "/" +
                item.expirationYear.toString().drop(2)

            when (item.themeId?.toInt()) {
                0 -> previewView.setBackgroundResource(R.drawable.card_bg_one)
                1 -> previewView.setBackgroundResource(R.drawable.card_bg_two)
                2 -> previewView.setBackgroundResource(R.drawable.card_bg_three)
                3 -> previewView.setBackgroundResource(R.drawable.card_bg_four)
                else -> previewView.setBackgroundResource(R.drawable.card_bg_five)
            }

            previewView.setOnClickListener {
                MainActivity.navController.navigate(R.id.cardDetailsFragment)
            }
        }
    }

    override fun getItemCount(): Int = listOfCards.size

    /**
     * Inside this method data will be displayed at the specified position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfCards[position]
        holder.bindView(item, position)
    }

    /**
     * Inside this method we specify the layout that each item of the RecyclerView should use
     * onCreateViewHolder has return type of RecyclerView.ViewHolder which represent each row of recyclerView.
     * Using Inflator get the view of above defined job_item and pass it to viewholder
     * constructor and then return.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_card_home, parent, false)
        return ViewHolder(itemView)
    }
}

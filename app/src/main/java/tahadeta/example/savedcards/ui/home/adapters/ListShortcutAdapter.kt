package tahadeta.example.savedcards.ui.home.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import tahadeta.example.savedcards.R
import tahadeta.example.savedcards.data.Shortcut

class ListShortcutAdapter(
    private val context: Context?,
    private var listOfShortcuts: ArrayList<Shortcut>
) : RecyclerView.Adapter<ListShortcutAdapter.ViewHolder>() {

    /**
     * Find all the views of the list item
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var title: TextView
        lateinit var icon: ImageView
        lateinit var container: ConstraintLayout
        lateinit var rightBg: View

        /**
         * Show the data in the views
         */
        fun bindView(item: Shortcut, position: Int) {
            title = itemView.findViewById(R.id.shortcut_title)
            icon = itemView.findViewById(R.id.shortcut_icon)
            container = itemView.findViewById(R.id.container_bg)
            rightBg = itemView.findViewById(R.id.right_bg)

            title.text = item.title

            when (item.iconId?.toInt()) {
                0 -> icon.setImageResource(R.drawable.scanner_card)
                1 -> icon.setImageResource(R.drawable.secure_app_icon)
                2 -> icon.setImageResource(R.drawable.info_icon)
                else -> icon.setImageResource(R.drawable.info_icon)
            }

            if (item.isEnabled == true) {
                container.setBackgroundResource(R.drawable.shortcut_bg)
            } else {
                container.setBackgroundResource(R.drawable.set_secret_code_bg)
                title.setTextColor(Color.parseColor("#e0f7fa"))
                rightBg.setBackgroundResource(R.drawable.bottom_shortcut_gold_bg)
            }
            container.setOnClickListener {
                when (item.iconId?.toInt()) {
                    0 -> tahadeta.example.savedcards.MainActivity.navController.navigate(R.id.scanCardFragment)
                    1 -> tahadeta.example.savedcards.MainActivity.navController.navigate(R.id.secureAppFragment)
                    2 -> tahadeta.example.savedcards.MainActivity.navController.navigate(R.id.appInfoFragment)
                    else -> tahadeta.example.savedcards.MainActivity.navController.navigate(R.id.secureAppFragment)
                }
            }
        }
    }

    override fun getItemCount(): Int = listOfShortcuts.size

    /**
     * Inside this method data will be displayed at the specified position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfShortcuts[position]
        holder.bindView(item, position)
    }

    /**
     * Inside this method we specify the layout that each item of the RecyclerView should use
     * onCreateViewHolder has return type of RecyclerView.ViewHolder which represent each row of recyclerView.
     * Using Inflator get the view of above defined job_item and pass it to viewholder
     * constructor and then return.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_shortcut, parent, false)
        return ViewHolder(itemView)
    }
}

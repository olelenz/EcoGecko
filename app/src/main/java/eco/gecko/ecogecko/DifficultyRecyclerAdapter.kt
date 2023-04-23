package eco.gecko.ecogecko

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DifficultyRecyclerAdapter(private val difficultyFragment: FragmentDifficulty) : RecyclerView.Adapter<DifficultyRecyclerAdapter.CardViewHolder?>() {

    private var level = MainActivity.setOfDifficulties.toList()

    inner class CardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var itemTitle: TextView? = null

        init {
            if (itemView != null) {
                itemTitle = itemView.findViewById(R.id.difficulty)
            }

            // by pressing one card, go to DetailsFragment
            itemView?.setOnClickListener {
                onclickLevel(this.itemTitle?.text.toString().toInt())
            }
        }
    }

    fun onclickLevel(level: Int){
        println("level: "+level)
        // add transaction to new fragment
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {

        return CardViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.recycler_difficulties, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return level.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        layoutParams.height = 200
        layoutParams.width = 200
        holder.itemView.layoutParams = layoutParams

        holder.itemTitle?.text = level[position].toString()
    }

}
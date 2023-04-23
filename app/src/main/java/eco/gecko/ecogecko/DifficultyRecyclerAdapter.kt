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
    
    private var level = listOf("1","2","3","4","5","6")

    inner class CardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var itemTitle: TextView? = null

        init {
            if (itemView != null) {
                itemTitle = itemView.findViewById(R.id.difficulty)
            }

            // by pressing one card, go to DetailsFragment
            itemView?.setOnClickListener { }
        }
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
        holder.itemTitle?.text = level[position]


        val layoutParams = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        layoutParams.height = 200
        layoutParams.width = 200
        holder.itemView.layoutParams = layoutParams

    }

}
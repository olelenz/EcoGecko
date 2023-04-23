package eco.gecko.ecogecko

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DifficultyRecyclerAdapter(private val difficultyFragment: FragmentDifficulty) : RecyclerView.Adapter<DifficultyRecyclerAdapter.CardViewHolder?>() {

    private var difficulty = MainActivity.setOfDifficulties.toList()

    inner class CardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var itemTitle: TextView? = null

        init {
            if (itemView != null) {
                itemTitle = itemView.findViewById(R.id.difficulty)
            }

            // by pressing one card, go to DetailsFragment
            itemView?.setOnClickListener {
                onclickLevel(difficulty[this.itemTitle?.text.toString().toInt() - 1])
            }
        }
    }

    fun onclickLevel(level: Int){
        val fragmentManager = difficultyFragment.requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, FragmentLevelOverview(level))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {

        return CardViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.recycler_difficulties, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return difficulty.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        layoutParams.height = 200
        layoutParams.width = 200
        holder.itemView.layoutParams = layoutParams

        var counter = position + 1

        holder.itemTitle?.text = counter.toString()
    }
}
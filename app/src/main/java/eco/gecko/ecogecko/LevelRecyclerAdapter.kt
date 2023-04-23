package eco.gecko.ecogecko

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LevelRecyclerAdapter(private val fragmentLevelOverview: FragmentLevelOverview) : RecyclerView.Adapter<LevelRecyclerAdapter.CardViewHolder?>() {

    var level = fragmentLevelOverview.list


    inner class CardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var itemTitle: TextView? = null

        init {
            if (itemView != null) {
                itemTitle = itemView.findViewById(R.id.difficulty)
            }

            // by pressing one card, go to DetailsFragment
            itemView?.setOnClickListener {
                openGame(MainActivity.boardList[level[position]])
            }
        }
    }

    private fun openGame(inputString: String) {
        val fragmentManager = fragmentLevelOverview.requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, FragmentGame(inputString))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {

        return CardViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.recycler_level, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return level.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.itemTitle?.text = level[position].toString()
    }
}
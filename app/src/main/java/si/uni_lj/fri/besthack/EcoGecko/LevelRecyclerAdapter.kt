package si.uni_lj.fri.besthack.EcoGecko

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView

class LevelRecyclerAdapter(private val fragmentLevelOverview: FragmentLevelOverview) : RecyclerView.Adapter<LevelRecyclerAdapter.CardViewHolder?>() {

    var level = fragmentLevelOverview.list
    companion object{
        var flag: Boolean = true
    }


    inner class CardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var itemTitle: TextView? = null

        init {
            if (itemView != null) {
                itemTitle = itemView.findViewById(R.id.difficulty)
            }

            // by pressing one card, go to DetailsFragment
            itemView?.setOnClickListener {
                if (itemTitle?.text.toString() != "") {
                    //openGame(MainActivity.boardList[itemTitle?.text.toString().toInt() - 1])
                    openGame(MainActivity.boardList[level.get(position)])
                }
            }
        }
    }

    private fun openGame(inputString: String) {
        val fragmentManager = fragmentLevelOverview.requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(android.R.id.content, FragmentGame(inputString))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {
        flag = true
        return CardViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.recycler_level, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return level.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        var sp2 = fragmentLevelOverview.requireContext()
            .getSharedPreferences("my_prefs2", Context.MODE_PRIVATE)
        val id: Int = sp2?.getInt("id", 1)!!.toInt()
        if (id >= level.get(position)) {
            holder.itemTitle?.text = level.get(position).toString()
        }

    }
}
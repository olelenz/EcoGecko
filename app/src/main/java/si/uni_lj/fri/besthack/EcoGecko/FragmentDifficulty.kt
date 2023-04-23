package eco.gecko.ecogecko

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import si.uni_lj.fri.besthack.EcoGecko.R

class FragmentDifficulty : Fragment() {

    companion object {
        private var recyclerView: RecyclerView? = null
        private var adapter: RecyclerView.Adapter<*>? = null
        private const val column: Int = 4
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_difficulty, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_difficulties)
        val layoutManager = GridLayoutManager(context, column)
        recyclerView?.layoutManager = layoutManager
        adapter = DifficultyRecyclerAdapter(this)
        recyclerView?.adapter = adapter

        return view
    }

}
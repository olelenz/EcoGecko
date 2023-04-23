package eco.gecko.ecogecko

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentDifficulty : Fragment() {

    companion object {
        private var recyclerView: RecyclerView? = null
        //private var layoutManager: RecyclerView.LayoutManager? = null
        private var adapter: RecyclerView.Adapter<*>? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_difficulty, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager = GridLayoutManager(context, 4)
        //layoutManager = LinearLayoutManager(view.context)
        recyclerView?.layoutManager = layoutManager
        adapter = DifficultyRecyclerAdapter(this)
        recyclerView?.adapter = adapter

        return view
    }

}
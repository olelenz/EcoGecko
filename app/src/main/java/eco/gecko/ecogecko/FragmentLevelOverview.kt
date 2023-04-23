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

class FragmentLevelOverview : Fragment() {

    companion object {
        private var recyclerView: RecyclerView? = null
        private var adapter: RecyclerView.Adapter<*>? = null
        private var layoutManager: RecyclerView.LayoutManager? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_level_overview, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_level)
        layoutManager = LinearLayoutManager(view.context)
        recyclerView?.layoutManager = layoutManager
        adapter = LevelRecyclerAdapter(this)
        recyclerView?.adapter = adapter

        return view
    }


}
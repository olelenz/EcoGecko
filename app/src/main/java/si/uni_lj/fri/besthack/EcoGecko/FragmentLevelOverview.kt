package eco.gecko.ecogecko

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentLevelOverview(var level: Int) : Fragment() {
    val list = setList()

    companion object {
        private var recyclerView: RecyclerView? = null
        private var adapter: RecyclerView.Adapter<*>? = null
        private var layoutManager: RecyclerView.LayoutManager? = null
    }

    private fun setList(): List<Int>{
        var out: MutableList<String> = mutableListOf()
        var indices: List<Int> = MainActivity.levelList.indexesOf(level)
        for(index in indices){
            out.add(MainActivity.boardList[index])
        }
        return indices
    }

    fun <E> Iterable<E>.indexesOf(e: E)
            = mapIndexedNotNull{ index, elem -> index.takeIf{ elem == e } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_level_overview, container, false)

        val textView = view.findViewById<TextView>(R.id.cur_dif)
        var curDif = "You selected difficulty: $level"
        textView.text = curDif

        recyclerView = view.findViewById(R.id.recycler_view_level)
        layoutManager = LinearLayoutManager(view.context)
        recyclerView?.layoutManager = layoutManager
        adapter = LevelRecyclerAdapter(this)
        recyclerView?.adapter = adapter


        return view
    }


}
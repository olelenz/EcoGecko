package eco.gecko.ecogecko

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentUI : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ui, container, false)

        val n = 6 // replace with the desired value of N
        val dataSource = (1..n*n).toList()

        val gridView = view.findViewById<GridView>(R.id.gridView)
        gridView.adapter = GridAdapter(dataSource)

        return view
    }
}

/**
 * GRID CLass with basic functions
 */

class GridAdapter(private val dataSource: List<Int>) : BaseAdapter() {
    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = dataSource[position].toString()
        return view
    }
}
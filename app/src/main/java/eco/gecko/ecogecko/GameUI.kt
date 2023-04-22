package eco.gecko.ecogecko

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment


class GameUI : Fragment() {

    private lateinit var grid: GridView
    companion object {
        private const val n : Int = 6
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_ui, container, false)

        // initializing empty board
        grid = view.findViewById(R.id.gridView)

        // newGame Button
        val newGame = view.findViewById<Button>(R.id.newGame)
        newGame.setOnClickListener{ createNewGame() }

        return view
    }

    private fun createNewGame() {
        val adapter = GridAdapter()
        grid.adapter = adapter
    }
}

class GridAdapter() : BaseAdapter() {

    val dataSource = listOf(1, 0, 1, 1, 0, 0,
                            0, 1, 0, 1, 1, 0,
                            1, 0, 1, 1, 0, 0,
                            0, 1, 0, 1, 1, 0,
                            1, 0, 1, 1, 0, 0,
                            0, 1, 0, 1, 1, 0)
    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)

        view.setBackgroundResource(R.drawable.tiles_layout)
        view.setPadding(12,0,12,0)

        return view
    }
}



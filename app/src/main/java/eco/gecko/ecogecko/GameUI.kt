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
        val dataSource = listOf(1, 0, 1, 1, 0, 0,
                                0, 1, 0, 1, 1, 0,
                                1, 0, 1, 1, 0, 0,
                                0, 1, 0, 1, 1, 0,
                                1, 0, 1, 1, 0, 0,
                                0, 1, 0, 1, 1, 0)
        val adapter = GridAdapter(dataSource, n*n)
        grid.adapter = adapter
    }
}

/**
 * GRID CLass with basic functions
 */

class GridAdapter(private val dataSource: List<Int>, private val gridSize: Int) : BaseAdapter() {
    private val rowCount = kotlin.math.ceil(dataSource.size.toDouble() / gridSize).toInt()
    private val gridDataSource = dataSource + List((rowCount * gridSize) - dataSource.size) { 0 }

    override fun getCount(): Int = gridDataSource.size

    override fun getItem(position: Int): Any = gridDataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.tile, parent, false)

        // set background color based on item value
        when (gridDataSource[position]) {
            0 -> view.setBackgroundColor(Color.BLUE)
            1 -> view.setBackgroundColor(Color.RED)
        }

        return view
    }
}
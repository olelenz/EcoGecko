package eco.gecko.ecogecko

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayout


class GameUI : Fragment() {

    private lateinit var grid: GridView
    private lateinit var flexbox: FlexboxLayout
    private lateinit var displayManager: DisplayMetrics
    companion object {
        private const val n : Int = 6
        private var w : Int = 0
        private var h : Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_ui, container, false)

        displayManager = DisplayMetrics()
        (context as Activity) .getWindowManager().defaultDisplay.getMetrics(displayManager)
        w = displayManager.widthPixels
        h = displayManager.heightPixels

        println(w)

        // initializing empty board
        grid = view.findViewById(R.id.gridView)
        flexbox = view.findViewById(R.id.flexbox)

        // newGame Button
        val newGame = view.findViewById<Button>(R.id.newGame)
        newGame.setOnClickListener{ createNewGame() }

        return view
    }

    private fun createNewGame() {
        val adapter = GridAdapter(w)
        // build grid
        grid.adapter = adapter

        drawButton()

    }
    private fun drawButton() {
        // FIXME Height
        val tile = Button(context)
        val w = flexbox.width / n
        val h = flexbox.width / (n+1)
        tile.setBackgroundResource(R.drawable.tiles_layout)
        tile.width = w*2
        tile.height = h*2

        val layoutParams = FlexboxLayout.LayoutParams(
            FlexboxLayout.LayoutParams.WRAP_CONTENT,
            FlexboxLayout.LayoutParams.WRAP_CONTENT
        )
        tile.layoutParams = layoutParams
        flexbox.addView(tile)
    }
}

class GridAdapter(var width: Int) : BaseAdapter() {

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

        // pleaseeeee keep it like this, important for grid
        view.setBackgroundResource(R.drawable.fields_layout)
        val button = view.findViewById<Button>(R.id.field)
        button.width = width / 7
        button.height = width / 7

        return view
    }
}



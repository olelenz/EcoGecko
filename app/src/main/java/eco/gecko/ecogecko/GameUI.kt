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
import java.util.Dictionary


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
        adapter.fillData()
        grid.adapter = adapter
    }
}

class GridAdapter() : BaseAdapter() {

    val colourDictionary = mapOf<Int, Int>(
        0 to Color.WHITE,
        1 to Color.RED,
        2 to Color.BLUE,
        3 to Color.BLACK,
        4 to Color.CYAN,
        5 to Color.DKGRAY,
        6 to Color.GREEN,
        7 to Color.LTGRAY,
        8 to Color.MAGENTA,
        9 to Color.YELLOW,
        10 to Color.RED,
        11 to Color.BLUE,
        12 to Color.CYAN,
        13 to Color.GREEN
    )


    val dataSourceOld = listOf(1, 0, 1, 1, 0, 0,
                            0, 1, 0, 1, 1, 0,
                            1, 0, 1, 1, 0, 0,
                            0, 1, 0, 1, 1, 0,
                            1, 0, 1, 1, 0, 0,
                            0, 1, 0, 1, 1, 0)

    val nullList: MutableList<Tile?> = mutableListOf()
    val dataSource_no = mutableListOf<Tile>()
    val dataSource = arrayOfNulls<Tile>(36)
    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = 1

    override fun getItemId(position: Int): Long = position.toLong()

    fun fillData(){
        //nullList.fill(null)
        var game: GameBoard = GameBoard("ooBBoxDDDKooAAJKoMooJEEMIFFLooIGGLox")
        var id: Int = 1
        for(tile in game.tiles){
            for (i in 0 until tile.getPositionX().size){
                dataSource[(tile.getPositionX()[i]+tile.getPositionY()[i]*6)] = tile
            }
            id++
        }
        println(dataSource)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)

        view.setBackgroundResource(R.drawable.tiles_layout)
        view.setPadding(12,0,12,0)

        var button: Button = view.findViewById<Button>(R.id.tile) as Button
        //button.text = dataSource[position].toString()

        when(dataSource[position]?.getType()){
            GameBoard.Companion.TileType.WALL -> button.setBackgroundColor(Color.BLACK)
            GameBoard.Companion.TileType.CLOUD -> button.setBackgroundColor(Color.RED)
            GameBoard.Companion.TileType.NORMAL -> button.setBackgroundColor(Color.BLUE)
            else -> {button.setBackgroundColor(Color.WHITE)}
        }
        return view
    }
}



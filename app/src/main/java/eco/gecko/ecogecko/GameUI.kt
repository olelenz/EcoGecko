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

        view?.findViewById<Button>(R.id.up_button)?.setOnClickListener(){moveUp()}
    }

    private fun moveUp(){
        try {
            println(GridAdapter.gameComp.tiles[6].getPositionY())
            GridAdapter.currentTile.moveUp(-1)
            println("nice")
            println(GridAdapter.gameComp.tiles[6].getPositionY())
        } catch (e:java.lang.Exception){
            println("not nice")
            println(GridAdapter.currentTile.getPositionX())
            println(GridAdapter.currentTile.getPositionY())
            e.printStackTrace()
        }
    }

    private fun moveDown(){

    }

    private fun moveLeft(){

    }

    private fun moveRight(){

    }
}

class GridAdapter() : BaseAdapter() {
    companion object{
        lateinit var currentTile: Tile
        lateinit var gameComp: GameBoard
    }

    val colourDictionary = mapOf<Int, Int>(
        0 to Color.DKGRAY,
        1 to Color.YELLOW,
        2 to Color.BLUE,
        3 to Color.CYAN,
        4 to Color.DKGRAY,
        5 to Color.GREEN,
        6 to Color.LTGRAY,
        7 to Color.MAGENTA,
        8 to Color.YELLOW,
        9 to Color.BLUE,
        10 to Color.CYAN,
        11 to Color.GREEN,
        12 to Color.LTGRAY,
        13 to Color.MAGENTA,
        14 to Color.YELLOW
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
        gameComp = game
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
        button.setOnClickListener(){
            currentTile = dataSource[position]!!
            println(currentTile.getId())
        }

        when(dataSource[position]?.getType()){
            GameBoard.Companion.TileType.WALL -> {
                button.setBackgroundColor(Color.BLACK)
                button.isClickable = false
            }
            GameBoard.Companion.TileType.CLOUD -> button.setBackgroundColor(Color.RED)
            GameBoard.Companion.TileType.NORMAL -> {
                colourDictionary[dataSource[position]?.getId()]?.let {button.setBackgroundColor(it) }
            }
            else -> {
                button.setBackgroundColor(Color.WHITE)
                button.visibility = View.INVISIBLE  // TODO: may cause problems
            }
        }
        return view
    }
}



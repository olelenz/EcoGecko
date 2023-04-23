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


class FragmentGame : Fragment() {

    private lateinit var grid: GridView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        // initializing empty board
        grid = view.findViewById(R.id.gridView)

        // newGame Button
        val newGameButton = view.findViewById<Button>(R.id.newGameButton)
        newGameButton.setOnClickListener{ createNewGame() }

        // Return Button
        val returnButton = view.findViewById<Button>(R.id.returnButton)
        returnButton.setOnClickListener{ returnToMenu() }

        return view
    }

    private fun returnToMenu() {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, FragmentMenu())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun createNewGame() {
        val adapter = GridAdapter()
        adapter.fillData(null)
        grid.adapter = adapter

        view?.findViewById<Button>(R.id.up_button)?.setOnClickListener {moveUp()}
        view?.findViewById<Button>(R.id.down_button)?.setOnClickListener {moveDown()}
        view?.findViewById<Button>(R.id.left_button)?.setOnClickListener {moveLeft()}
        view?.findViewById<Button>(R.id.right_button)?.setOnClickListener {moveRight()}
    }

    private fun updateGame(){
        val adapter = GridAdapter()
        adapter.fillData(GridAdapter.gameComp)
        grid.adapter = adapter
    }

    private fun moveUp(){
        try {
            GridAdapter.currentTile.moveUp(-1)
            updateGame()
        } catch (e:java.lang.Exception){

        }
    }

    private fun moveDown(){
        try {
            GridAdapter.currentTile.moveDown(1)
            updateGame()
        } catch (e:java.lang.Exception){

        }
    }

    private fun moveLeft(){
        try {
            GridAdapter.currentTile.moveLeft(-1)
            updateGame()
        } catch (e:java.lang.Exception){

        }
    }

    private fun moveRight(){
        try {
            GridAdapter.currentTile.moveRight(1)
            updateGame()
        } catch (e:java.lang.Exception){

        }
    }
}

class GridAdapter : BaseAdapter() {

    private val dataSource = arrayOfNulls<Tile>(36)

    private val colourDictionary = mapOf(
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
    companion object{
        lateinit var currentTile: Tile
        lateinit var gameComp: GameBoard
    }
    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = 1

    override fun getItemId(position: Int): Long = position.toLong()

    fun fillData(game: GameBoard?){
        if (game == null){
            gameComp = GameBoard("ooBBoxDDDKooAAJKoMooJEEMIFFLooIGGLox")
        } else {
            gameComp = game
        }
        var id = 1
        for(tile in gameComp.tiles){
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
        // TODO think about it
        view.setPadding(12,0,12,0)

        val button: Button = view.findViewById(R.id.tile) as Button
        button.setOnClickListener{
            currentTile = dataSource[position]!!
            println(currentTile.getId())
        }

        when(dataSource[position]?.getType()){
            GameBoard.Companion.TileType.WALL -> {
                button.setText(R.string.wall)
                button.setBackgroundColor(Color.TRANSPARENT)
            }
            GameBoard.Companion.TileType.CLOUD -> {
                button.setText(R.string.car)
                button.setBackgroundColor(Color.TRANSPARENT)
            }
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



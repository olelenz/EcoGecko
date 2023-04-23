package si.uni_lj.fri.besthack.EcoGecko


/*
    Using https://www.michaelfogleman.com/rush/#DatabaseDownload for the initial setup data
    example input string: ooBBoxDDDKooAAJKoMooJEEMIFFLooIGGLox
 */
class GameBoard(boardRep: String) {

    var tiles: MutableList<Tile> = parseInputString(boardRep)
    var flagboard: MutableList<MutableList<Boolean>> = setFlagboard()

    companion object{
        enum class TileType{
            CLOUD, WALL, NORMAL
        }
        private const val BOARD_SIZE_X: Int = 6
        private const val BOARD_SIZE_Y: Int = 6
        /*
        Function to check if a set of coordinates are inside the board
            */
        fun checkCoordinates(xPositions: MutableList<Int>, yPositions: MutableList<Int>): Boolean{
            if (xPositions.size != yPositions.size){
                throw IllegalStateException("Position arrays do not have the same size!")
            }
            var ind: Int = 0
            var out: Boolean = true
            while (ind < xPositions.size){
                out = out && checkCoordinateValid(xPositions[ind],yPositions[ind])
                ind++
            }
            return out
        }

        /*
        Function to check single coordinate and check if it is inside the board
            */
        fun checkCoordinateValid(x: Int, y: Int): Boolean{
            return !(x < 0 || y < 0 || x >= BOARD_SIZE_X || y >= BOARD_SIZE_Y)
        }
    }

    public fun parseInputString(inp: String): MutableList<Tile>{
        var tileDictionary = mutableMapOf<Char, Tile>()
        var inp = inp + " "
        var out: MutableList<Tile> = mutableListOf()
        var ind: Int = 1
        var currentChar: Char = inp[0]
        var readChar = inp[0]
        var tileStartIndex: Int = 0
        while (ind < 36 + 1){
            readChar = inp[ind]
            //println("read char: "+readChar)
            if (readChar == currentChar){  // still in same tile

            } else { // no longer in tile
                //println("found tile: " + currentChar + ", startIndex: " + tileStartIndex + ", endIndex: " + (ind - 1))
                when(currentChar){
                    'o' -> {  // empty
                    }
                    'x' -> {  // wall
                        val tile: Tile = Tile(this, getXPositions(tileStartIndex, ind-1), getYPositions(tileStartIndex, ind-1), TileType.WALL)
                        out.add(tile)
                    }
                    'A' -> {  // cloud
                        if (currentChar in tileDictionary){
                            val tile: Tile? = tileDictionary[currentChar]
                            tile?.addPositionsX(getXPositions(tileStartIndex, ind-1))
                            tile?.addPositionsY(getYPositions(tileStartIndex, ind-1))
                        } else {
                            val tile: Tile = Tile(this, getXPositions(tileStartIndex, ind-1), getYPositions(tileStartIndex, ind-1), TileType.CLOUD)
                            out.add(tile)
                            tileDictionary[currentChar] = tile
                        }
                    }
                    else -> {  // tile here
                        if (currentChar in tileDictionary){
                            val tile: Tile? = tileDictionary[currentChar]
                            tile?.addPositionsX(getXPositions(tileStartIndex, ind-1))
                            tile?.addPositionsY(getYPositions(tileStartIndex, ind-1))
                        } else {
                            val tile: Tile = Tile(this, getXPositions(tileStartIndex, ind-1), getYPositions(tileStartIndex, ind-1), TileType.NORMAL)
                            out.add(tile)
                            tileDictionary[currentChar] = tile
                        }
                    }
                }
                currentChar = readChar
                // update start index for next tile
                tileStartIndex = ind
            }
            ind++
        }
        /*println(out)
        for (entry in out){
            println("------------------")
            println("new tile:")
            println(entry.getType())
            println(entry.getPositionX())
            println(entry.getPositionY())
        }*/
        return out
    }

    private fun getXPositions(startIndex: Int, endIndex: Int): MutableList<Int>{
        var out: MutableList<Int> = mutableListOf()
        for (i in startIndex .. endIndex){
            out.add(i%6)
        }
        return out
    }

    private fun getYPositions(startIndex: Int, endIndex: Int): MutableList<Int>{
        var out: MutableList<Int> = mutableListOf()
        for (i in startIndex .. endIndex){
            out.add(startIndex/6)
        }
        return out
    }

    private fun setFlagboard(): MutableList<MutableList<Boolean>>{
        var init: MutableList<MutableList<Boolean>> = mutableListOf(mutableListOf(false,false,false,false,false,false), mutableListOf(false,false,false,false,false,false), mutableListOf(false,false,false,false,false,false), mutableListOf(false,false,false,false,false,false), mutableListOf(false,false,false,false,false,false), mutableListOf(false,false,false,false,false,false))
        for(tile in tiles){
            val xPos = tile.getPositionX()
            val yPos = tile.getPositionY()
            for (i in 0 until xPos.size){
                init[xPos[i]][yPos[i]] = true
            }
        }
        return init
    }


}
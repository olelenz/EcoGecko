package eco.gecko.ecogecko

class GameBoard {

    companion object{
        private const val BOARD_SIZE_X: Int = 6
        private const val BOARD_SIZE_Y: Int = 6

        /*
        Function to check if a set of coordinates are inside the board
            */
        fun checkCoordinates(xPositions: Array<Int>, yPositions: Array<Int>): Boolean{
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


}
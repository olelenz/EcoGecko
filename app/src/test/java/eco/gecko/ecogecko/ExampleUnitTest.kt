package eco.gecko.ecogecko

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

   @Test
   fun parseInput(){
       var gameBoard: GameBoard = GameBoard("ooBBoxDDDKooAAJKoMooJEEMIFFLooIGGLox")
       //gameBoard.parseInputString("ooBBoxDDDKooAAJKoMooJEEMIFFLooIGGLox")
       gameBoard.tiles[5].collides(mutableListOf(5, 5), mutableListOf(1, 2))
       assert(true)
   }

    @Test
    fun parseInputHorizontalChange(){
        var gameBoard: GameBoard = GameBoard("ooBBoxDDDKooAAJKoMooJEEMIFFLooIGGLox")
        //gameBoard.parseInputString("ooBBoxDDDKooAAJKoMooJEEMIFFLooIGGLox")
        gameBoard.tiles[0].collides(mutableListOf(3, 4), mutableListOf(0, 0))
        //gameBoard.tiles[0].collides(mutableListOf(2, 3), mutableListOf(1, 1))
        assert(true)
    }
}
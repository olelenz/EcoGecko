package si.uni_lj.fri.besthack.EcoGecko

import si.uni_lj.fri.besthack.EcoGecko.GameBoard.Companion.checkCoordinates
import java.util.Collections.max
import java.util.Collections.min

class Tile(private var parentBoard: GameBoard, private var positionsX: MutableList<Int>, private var positionsY: MutableList<Int>, private val type: GameBoard.Companion.TileType) {

    companion object{
        var idCreator: Int = 0
    }
    // false for moving sideways, true for moving up and down
    private var sight: Boolean = setSight()
    private var id: Int = createId()

    private fun createId(): Int{
        return idCreator++
    }

    public fun getId(): Int {
        return id
    }

    public fun getSight(): Boolean{
        return sight
    }
    public fun getType(): GameBoard.Companion.TileType{
        return type
    }

    public fun getPositionX():MutableList<Int>{
        return positionsX
    }

    public fun getPositionY():MutableList<Int>{
        return positionsY
    }

    public fun addPositionsX(positionsXInp: MutableList<Int>){
        positionsX.addAll(positionsXInp)
    }

    public fun addPositionsY(positionsYInp: MutableList<Int>){
        positionsY.addAll(positionsYInp)
    }

    public fun collides(newXPositions: MutableList<Int>, newYPositions: MutableList<Int>, change: Int): Boolean{
        // old positions
        // number of steps taken -> calculate using old and new positions
        // get flagboard
        // check if new positions are empty
        // remove old positions in flagboard
        // add new position in flagboard
        /*positionsX
        positionsY
        newXPositions
        newYPositions*/

        if (sight){  // up or down
            var minY = min(positionsY)
            var newMinY = min(newYPositions)
            var heightChange: Int = change//newMinY - minY
            println("height change: "+heightChange)
            if (heightChange == 0){
                println("not possible height")
                return true
            }
            if(heightChange < 0){
                for (i in -1 downTo heightChange){
                    if (parentBoard.flagboard[positionsX[0]][minY+i]) {
                        println("is blocked")
                        return true
                    }
                }
            } else {
                for(i in 1 .. heightChange){
                    println(max(positionsY))
                    if (parentBoard.flagboard[positionsX[0]][max(positionsY)+i]){
                        println("is blocked")
                        return true
                    }
                }
            }
        } else {  // left or right
            var minX = min(positionsX)
            var newMinX = min(newXPositions)
            var horizontalChange: Int = change//newMinX - minX
            println("horizontal change: "+horizontalChange)
            if (horizontalChange == 0){
                println("not possible horizontal")
                return true
            }
            if(horizontalChange < 0){
                for (i in -1 downTo horizontalChange){
                    if (parentBoard.flagboard[minX+i][positionsY[0]]){
                        println("is blocked")
                        return true
                    }
                }
            } else {
                for(i in 1 .. horizontalChange){
                    if (parentBoard.flagboard[max(positionsX)+i][positionsY[0]]){
                        println("is blocked")
                        return true
                    }
                }
            }
        }

        // not blocked -> change flagboard
        updateFlagboard(newXPositions, newYPositions)
        return false
    }

    fun updateFlagboard(newXPositions: MutableList<Int>, newYPositions: MutableList<Int>){
        for (i in 0 until positionsX.size){
            parentBoard.flagboard[positionsX[i]][positionsY[i]] = false  // remove old
            parentBoard.flagboard[newXPositions[i]][newYPositions[i]] = true  // add new
        }
    }

    public fun checkWin(): Boolean{
        if(type == GameBoard.Companion.TileType.CLOUD && max(positionsX) == 5){
            return true
        }
        return false
    }

    public fun moveUp(amount: Int) {
        if (!sight || type == GameBoard.Companion.TileType.WALL){
            throw RuntimeException("Can not move up!")
        }
        var newPositionsY = positionsY.toMutableList()
        for (ind in 0 until positionsY.size){
            newPositionsY[ind] = newPositionsY[ind] + amount
        }

        // check for collisions
        if (collides(positionsX, newPositionsY, amount)){
            throw RuntimeException("Can not move up! (Collision)")
        }

        if (checkCoordinates(positionsX, newPositionsY)){
            positionsY = newPositionsY.toMutableList()
        } else {
            throw IndexOutOfBoundsException("You can not move here!")
        }
    }

    public fun moveDown(amount: Int) {
        if (!sight || type == GameBoard.Companion.TileType.WALL){
            throw RuntimeException("Can not move down!")
        }
        var newPositionsY = positionsY.toMutableList()
        for (ind in 0 until positionsY.size){
            newPositionsY[ind] = newPositionsY[ind] + amount
        }

        // check for collisions
        if (collides(positionsX, newPositionsY, amount)){
            throw RuntimeException("Can not move down! (Collision)")
        }

        if (checkCoordinates(positionsX, newPositionsY)){
            positionsY = newPositionsY.toMutableList()
        } else {
            throw IndexOutOfBoundsException("You can not move here!")
        }
    }

    public fun moveLeft(amount: Int) {
        if (sight || type == GameBoard.Companion.TileType.WALL){
            throw RuntimeException("Can not move left!")
        }
        var newPositionsX = positionsX.toMutableList()
        for (ind in 0 until positionsX.size){
            newPositionsX[ind] = newPositionsX[ind] + amount
        }

        // check for collisions
        if (collides(newPositionsX, positionsY, amount)){
            throw RuntimeException("Can not move left! (Collision)")
        }

        if (checkCoordinates(newPositionsX, positionsY)){
            positionsX = newPositionsX.toMutableList()
        } else {
            throw IndexOutOfBoundsException("You can not move here!")
        }
    }

    public fun moveRight(amount: Int) {
        if (sight || type == GameBoard.Companion.TileType.WALL){
            throw RuntimeException("Can not move right!")
        }
        var newPositionsX = positionsX.toMutableList()
        for (ind in 0 until positionsX.size){
            newPositionsX[ind] = newPositionsX[ind] + amount
        }

        // check for collisions
        if (collides(newPositionsX, positionsY, amount)){
            throw RuntimeException("Can not move right! (Collision)")
        }

        if (checkCoordinates(newPositionsX, positionsY)){
            positionsX = newPositionsX.toMutableList()
        } else {
            throw IndexOutOfBoundsException("You can not move here!")
        }
        /*
        if (checkWin()){
            println("you won!")
        }*/
    }

    private fun setSight(): Boolean{
        return positionsX.toSet().size == 1  // true if moving up and down, false otherwise
    }
}
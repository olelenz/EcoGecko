package eco.gecko.ecogecko

import eco.gecko.ecogecko.GameBoard.Companion.checkCoordinateValid
import eco.gecko.ecogecko.GameBoard.Companion.checkCoordinates

class Tile(private var positionsX: Array<Int>, private var positionsY: Array<Int>) {
    public fun getPositionX():Array<Int>{
        return positionsX
    }

    public fun getPositionY():Array<Int>{
        return positionsY
    }

    public fun moveUp() {
        var newPositionsY = positionsY.clone()
        for (ind in 0..positionsY.size){
            newPositionsY[0] = newPositionsY[0] + 1
        }
        if (checkCoordinates(positionsX, newPositionsY)){
            positionsY = newPositionsY.clone()
        } else {
            throw IndexOutOfBoundsException("You can not move here!")
        }
    }

    public fun moveDown() {
        var newPositionsY = positionsY.clone()
        for (ind in 0..positionsY.size){
            newPositionsY[0] = newPositionsY[0] - 1
        }
        if (checkCoordinates(positionsX, newPositionsY)){
            positionsY = newPositionsY.clone()
        } else {
            throw IndexOutOfBoundsException("You can not move here!")
        }
    }

    public fun moveLeft() {
        var newPositionsX = positionsX.clone()
        for (ind in 0..positionsX.size){
            newPositionsX[0] = newPositionsX[0] - 1
        }
        if (checkCoordinates(newPositionsX, positionsY)){
            positionsX = newPositionsX.clone()
        } else {
            throw IndexOutOfBoundsException("You can not move here!")
        }
    }

    public fun moveRight() {
        var newPositionsX = positionsX.clone()
        for (ind in 0..positionsX.size){
            newPositionsX[0] = newPositionsX[0] + 1
        }
        if (checkCoordinates(newPositionsX, positionsY)){
            positionsX = newPositionsX.clone()
        } else {
            throw IndexOutOfBoundsException("You can not move here!")
        }
    }





}
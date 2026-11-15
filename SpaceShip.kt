package game

// Representa a nave espacial e as suas dimensões.

data class SpaceShip(
    val x: Int,
    val y: Int
)

// Dimensões da nave.
const val SHIP_WIDTH = 50
const val SHIP_HEIGHT = 10

// Dimensões do retângulo amarelo.
const val DETAIL_WIDTH = 4
const val DETAIL_HEIGHT = 5

// Move a nave para a posição do rato.
fun moveShip(ship: SpaceShip, mouseX: Int, area: Area): SpaceShip {

    val newX = mouseX - SHIP_WIDTH / 2

    if (newX < 0) {
        return ship.copy(x = 0)
    }

    if (newX > area.width - SHIP_WIDTH) {
        return ship.copy(x = area.width - SHIP_WIDTH)
    }

    return ship.copy(x = newX)
}
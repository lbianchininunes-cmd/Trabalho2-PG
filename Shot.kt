package game
// Representa os projéteis da nave e dos aliens.
data class Shot(
    val x: Int,
    val y: Int,
    val speed: Int
)

// Dimensões dos projéteis.
const val SHOT_WIDTH = 4
const val SHOT_HEIGHT = 7

// Move o disparo da nave para cima.
fun moveShotUp(shot: Shot): Shot {
    return shot.copy(y = shot.y - shot.speed)
}

// Move o disparo dos aliens para baixo.
fun moveShotDown(shot: Shot): Shot {
    return shot.copy(y = shot.y + shot.speed)
}

// Verifica se dois projéteis colidiram.
fun shotsCollide(shot1: Shot, shot2: Shot): Boolean {

    if (shot1.x + SHOT_WIDTH <= shot2.x) return false
    if (shot1.x >= shot2.x + SHOT_WIDTH) return false
    if (shot1.y + SHOT_HEIGHT <= shot2.y) return false
    if (shot1.y >= shot2.y + SHOT_HEIGHT) return false

    return true
}

// Verifica se um projétil atingiu a nave.
fun shotHitsShip(shot: Shot, ship: SpaceShip): Boolean {

    if (shot.x + SHOT_WIDTH <= ship.x) return false
    if (shot.x >= ship.x + SHIP_WIDTH) return false
    if (shot.y + SHOT_HEIGHT <= ship.y) return false
    if (shot.y >= ship.y + SHIP_HEIGHT) return false

    return true
}
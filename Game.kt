package game

// estado completo do jogo
data class Game(
    val area: Area,
    val alienShots: List<Shot>,
    val ship: SpaceShip,
    val shipShot: Shot?,
    val over: Boolean,
    val frames: Int
)

//Cria o  estado inicial do jogo
fun createGame(): Game {
    val area = Area()

    val ship = SpaceShip(
        x = (area.width - SHIP_WIDTH) / 2,
        y = SHIP_Y
    )
     

     return Game(
        area = area,
        alienShots = emptyList(),
        ship = ship,
        shipShot = null,
        over = false,
        frames = 0
    )
}

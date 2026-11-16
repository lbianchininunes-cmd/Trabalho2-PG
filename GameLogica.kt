package game

//Numero de frames entre tentativas de criar disparos dos aliens
const val ALIEN_SHOT_INTERVAL = 18


//atualiza a posiçao do nave com o rato
fun updateShip(game: Game, mouseX: Int): SpaceShip {
    return moveShip(game.ship, mouseX, game.area)
}

// Cria um disparo da nave, se nao existir outro
fun fireShip(game: Game): Game {
    if (game.over) return game
    if (game.shipShot != null) return game

    val shotX = game.ship.x + (SHIP_WIDTH - SHOT_WIDTH) / 2
    val shotY = game.ship.y - DETAIL_HEIGHT - SHOT_HEIGHT

    val shot = Shot(
        x = shotX,
        y = shotY,
        speed = 4
    )

    return game.copy(shipShot = shot)
}

// Cria um disparo dos aliens com 50% de probabilidade
fun createAlienShot(game: Game): List<Shot> {
    if (Math.random() >= 0.5) {
        return game.alienShots
    }

    val shotX = (Math.random() * game.area.width).toInt()
    val shotSpeed = (Math.random() * 4).toInt() + 1

    val shot = Shot(
        x = shotX,
        y = 0,
        speed = shotSpeed
    )

    return game.alienShots + shot
}

// Vê se um disparo ainda está dentro da area do jogo
fun isShotInside(shot: Shot, area: Area): Boolean {
    return shot.y + SHOT_HEIGHT > 0 &&
           shot.y < area.height
}

// Move os disparos dos aliens e remove os que saíram da area
fun moveAlienShots(game: Game): List<Shot> {
    val movedShots = game.alienShots.map {
        moveShotDown(it)
    }

    return movedShots.filter {
        isShotInside(it, game.area)
    }
}

// Move o disparo da nave, se existir
fun moveShipShot(game: Game): Shot? {
    if (game.shipShot == null) {
        return null
    }

    val movedShot = moveShotUp(game.shipShot)

    if (isShotInside(movedShot, game.area)) {
        return movedShot
    }

    return null
}

// Remove os disparos dos aliens atingidos pelo disparo da nave
fun removeCollisions(
    alienShots: List<Shot>,
    shipShot: Shot?
): List<Shot> {
    if (shipShot == null) {
        return alienShots
    }

    return alienShots.filter {
        !shotsCollide(it, shipShot)
    }
}

// Atualiza o estado completo do jogo
fun updateGame(game: Game, mouseX: Int): Game {
    if (game.over) {
        return game
    }

    val newFrames = game.frames + 1
    val newShip = updateShip(game, mouseX)

    val movedAlienShots = moveAlienShots(game)
    val movedShipShot = moveShipShot(game)

    val collision = movedShipShot != null &&
        movedAlienShots.any {
            shotsCollide(it, movedShipShot)
        }

    val remainingAlienShots = removeCollisions(
        movedAlienShots,
        if (collision) movedShipShot else null
    )

    val remainingShipShot =
        if (collision) null else movedShipShot

    val shipHit = remainingAlienShots.any {
        shotHitsShip(it, newShip)
    }

    if (shipHit) {
        return game.copy(
            ship = newShip,
            alienShots = remainingAlienShots,
            shipShot = remainingShipShot,
            over = true,
            frames = newFrames
        )
    }

    val updatedGame = game.copy(
        ship = newShip,
        alienShots = remainingAlienShots,
        shipShot = remainingShipShot,
        frames = newFrames
    )

    if (newFrames % ALIEN_SHOT_INTERVAL == 0) {
        return updatedGame.copy(
            alienShots = createAlienShot(updatedGame)
        )
    }

    return updatedGame
}

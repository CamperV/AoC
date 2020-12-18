import kotlin.math.absoluteValue
import java.io.File

// extensions
fun Pair<Int, Int>.plusPair(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first + other.first, second + other.second)
}
fun Pair<Int, Int>.multiplyInt(other: Int): Pair<Int, Int> {
    return Pair(first * other, second * other)
}

class Ship() {
    // heading is determined (+x, +y), but defined in degrees
    var heading = 90
    val headingTranslation = mapOf<Int, Pair<Int, Int>>(
        -270 to Pair( 1,  0),
        -180 to Pair( 0, -1),
        -90  to Pair(-1,  0),
        0    to Pair( 0,  1),
        90   to Pair( 1,  0),
        180  to Pair( 0, -1),
        270  to Pair(-1,  0)
    )
    var position = Pair(0, 0)

    fun shift(by: Pair<Int, Int>) {
        position = position.plusPair(by)
    }

    fun rotate(by: Int) {
        // -270, -180, -90, 0, 90, 180, 270
        heading = (heading + by) % 360
    }

    fun move(by: Int) {
        position = position.plusPair(headingTranslation[heading]!!.multiplyInt(by))
    }
}

fun main() {
    var ship = Ship()

    for (command in File("input.txt").readLines()) {
        val preamble: Char = command[0]
        val value: Int = command.slice(1..command.length-1).toInt()

        when (preamble) {
            'N' -> ship.shift(Pair(0, value))
            'E' -> ship.shift(Pair(value, 0))
            'S' -> ship.shift(Pair(0, -value))
            'W' -> ship.shift(Pair(-value, 0))
            'L' -> ship.rotate(-value)  // negative implies CCW
            'R' -> ship.rotate(value)   // positive implies CW
            'F' -> ship.move(value)
            else -> println("$command is not a valid command.")
        }
        //println("Command: $command, ship: ${ship.position}")
    }

    println("Ship final position: ${ship.position}. MD: ${manhattanDist(Pair(0, 0), ship.position)}")
}

fun manhattanDist(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
    val combined = p1.plusPair(p2)
    return combined.first.absoluteValue + combined.second.absoluteValue
}
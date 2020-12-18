import kotlin.math.absoluteValue
import kotlin.math.sign
import java.io.File

// extensions
fun Pair<Int, Int>.plusPair(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first + other.first, second + other.second)
}
fun Pair<Int, Int>.multiplyInt(other: Int): Pair<Int, Int> {
    return Pair(first * other, second * other)
}

class Ship() {
    var vector = Pair(10, 1) // "waypoint"

    // so we don't have to calculate
    val cosLUT = mapOf<Int, Int>(
        0 to 1,
        90 to 0,
        180 to -1, 
        270 to 0
    )
    val sinLUT = mapOf<Int, Int>(
        0 to 0,
        90 to 1,
        180 to 0,
        270 to -1
    )
    val headingTranslation = mapOf<Int, Int>(
        -90 to 270,
        -180 to 180,
        -270 to 90
    )
    var position = Pair(0, 0)

    fun shiftVector(by: Pair<Int, Int>) {
        vector = vector.plusPair(by)
    }

    fun rotateVectorLUT(degrees: Int) {
        val deg = degrees.absoluteValue
        val x  = vector.first
        val y  = vector.second
        //
        val xp = degrees.sign * ((x * cosLUT[deg]!!) - (y * sinLUT[deg]!!))
        val yp = degrees.sign * ((x * sinLUT[deg]!!) + (y * cosLUT[deg]!!))
        vector = Pair(xp, yp)
    }

    fun rotateVector(degrees: Int) {
        val deg = headingTranslation.getOrDefault(degrees, degrees)
        when (deg) {
            90 -> vector = Pair(vector.second, -1*vector.first)
            180 -> vector = vector.multiplyInt(-1)
            270 -> vector = Pair(-1*vector.second, vector.first)
            else -> {
                println("Found illegal direction $deg, exiting")
                return
            }
        }
    }

    

    fun moveAlongVector(times: Int) {
        position = position.plusPair(vector.multiplyInt(times))
    }
}

fun main() {
    var ship = Ship()

    for (command in File("input.txt").readLines()) {
        val preamble: Char = command[0]
        val value: Int = command.slice(1..command.length-1).toInt()

        when (preamble) {
            'N' -> ship.shiftVector(Pair(0, value))
            'E' -> ship.shiftVector(Pair(value, 0))
            'S' -> ship.shiftVector(Pair(0, -value))
            'W' -> ship.shiftVector(Pair(-value, 0))
            'L' -> ship.rotateVector(-value)
            'R' -> ship.rotateVector(value)
            'F' -> ship.moveAlongVector(value)
            else -> println("$command is not a valid command.")
        }
        //println("Command: $command, ship: ${ship.position}, vec: ${ship.vector}")
    }

    println("Ship final position: ${ship.position}. MD: ${manhattanDist(Pair(0, 0), ship.position)}")
}

fun manhattanDist(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
    val combined = p1.plusPair(p2)
    return combined.first.absoluteValue + combined.second.absoluteValue
}
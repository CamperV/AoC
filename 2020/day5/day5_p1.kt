// what's the highest boarding pass ID?
import java.io.File

fun main() {
    val lines = File("input.txt").readLines()
    
    // max id of lines
    val maxID: Int? = lines.map{ getBoardingPassID(it) }.maxOrNull()
    println("Found maxID: $maxID")
}

// honestly, since this is fixed-depth, let's not get fancy w/ recursion
fun getBoardingPassID(bspString: String): Int {
    val maxRows = 128
    val maxCols = 8

    var currRow = Pair(0, maxRows-1)
    var currCol = Pair(0, maxCols-1)
    for (designator in bspString) {
        when(designator) {
            'F' -> currRow = lowerHalf(currRow)
            'B' -> currRow = upperHalf(currRow)
            'L' -> currCol = lowerHalf(currCol)
            'R' -> currCol = upperHalf(currCol)
            else -> {
                println("Invalid designator $designator in BSP-String $bspString")
            }
        }
    }

    return (currRow.first * 8) + currCol.first
}

fun lowerHalf(range: Pair<Int, Int>): Pair<Int, Int> {
    val halfRange = (range.second - range.first + 1) / 2
    return Pair(range.first, range.first + halfRange-1)
}

fun upperHalf(range: Pair<Int, Int>): Pair<Int, Int> {
    val halfRange = (range.second - range.first + 1) / 2
    return Pair(range.second - halfRange + 1, range.second)
}
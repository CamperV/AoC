import java.io.File

fun main() {
    // just frickin sort the entire thing first
    // since we know we have to use all of the adaptors... just count the gaps
    val sortedJoltage = listOf(0) + File("input.txt").readLines().map{ it.toInt() }.sorted()
    var joltGaps = mutableMapOf<Int, Int>(1 to 0, 2 to 0, 3 to 0)

    val numAdapt = sortedJoltage.size
    for ((index, joltage) in sortedJoltage.withIndex()) {
        val nextJoltage = if (index < numAdapt-1) sortedJoltage[index+1] else joltage+3
        val gap = nextJoltage - joltage
        joltGaps[gap] = joltGaps[gap]!! + 1
    }

    println(joltGaps)
    println(joltGaps[1]!!*joltGaps[3]!!)
}
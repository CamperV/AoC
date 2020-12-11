import java.io.File

fun main() {
    val lines = File("input.txt").readLines()

    val preambleLength = 25
    var queue = mutableListOf<Int>()

    var targetNumber = -1

    // since this algorithm must be at least N^2, let's keep it ordered
    // basically, instead of recalculating the sums, or storing them in a set,
    // let's prune the first preambleLength^2 options, and only recalculate the last preambleLength^2
    // the list.contains() method should short circuit anyway, so we don't super care about lookup times
    for (line in lines) {
        val curr = line.toInt()
        if (queue.size < preambleLength) {
            queue.add(curr)
            continue
        }

        // let's do it the naive way first
        val isValid = getCombinations(queue).contains(curr)
        if (!isValid) {
            println("$curr is invalid")
            targetNumber = curr
            break
        }

        queue.removeAt(0)
        queue.add(curr)
    }

    // now that we've uncovered the target number...
    // find the contiguous set of input that sums to it
    // then extract the lowest and highest numbers in the set

    // i could use a priority queue here to keep it nicely sorted as I continue to add to it
    // but I mean that's overkill for now
    // again... I strain against the spatial ineff. but I need to do it naively first
    // we'll just see if runtime is that bad
    for (ind in lines.indices) {

        var capture = false
        var rollingSum = 0
        var rollingQueue = mutableListOf<Int>()

        for (otherLine in lines.slice(ind..lines.size-1)) {
            val other = otherLine.toInt()
            rollingSum += other
            rollingQueue.add(other)

            // record the set
            if (rollingSum == targetNumber) { capture = true; break }
            if (rollingSum > targetNumber) { break }
        }

        if (capture) {
            val min = rollingQueue.minOrNull()!!
            val max = rollingQueue.maxOrNull()!!
            println("discovered weakness: ${min+max}")
            break
        }
    }
}

// none of the input numbers are < 0
fun getCombinations(options: MutableList<Int>): MutableSet<Int> {
    var retVal = mutableSetOf<Int>()

    options.forEach{
        a -> options.forEach{
            b -> if (a != b) retVal.add(a+b)
        }
    }
    return retVal
}
import java.io.File

fun main() {
    val preambleLength = 25
    var queue = mutableListOf<Int>()

    // since this algorithm must be at least N^2, let's keep it ordered
    // basically, instead of recalculating the sums, or storing them in a set,
    // let's prune the first preambleLength^2 options, and only recalculate the last preambleLength^2
    // the list.contains() method should short circuit anyway, so we don't super care about lookup times
    for (line in File("input.txt").readLines()) {
        val curr = line.toInt()
        if (queue.size < preambleLength) {
            queue.add(curr)
            continue
        }

        // let's do it the naive way first
        val isValid = getCombinations(queue).contains(curr)
        if (!isValid) {
            println("$curr is invalid")
            break
        }

        queue.removeAt(0)
        queue.add(curr)
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
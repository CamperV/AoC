import java.io.File

fun main() {
    val lines = File("input.txt").readLines()
    
    var runningCount = 0
    var currGroup = mutableSetOf<Char>()

    for (line in lines) {
        if (line.isNullOrBlank()) {
            runningCount += currGroup.size
            currGroup.clear()
            continue
        }

        line.forEach { currGroup.add(it) }
    }
    // finally:
    runningCount += currGroup.size

    println("Running count (final): $runningCount")
}
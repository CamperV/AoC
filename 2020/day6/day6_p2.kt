import java.io.File

fun main() {
    val lines = File("input.txt").readLines()
    
    var runningCount = 0
    var currGroup = setOf<Char>()

    // needed to use a flag here, because checking set.isEmpty() was causing the .intersect() to break when 
    // there was no intersection, and it set the set to empty
    var newGroup = true
    for (line in lines) {
        if (line.isNullOrBlank()) {
            runningCount += currGroup.size
            currGroup = setOf<Char>()
            newGroup = true
            continue
        }

        if (newGroup) currGroup = line.toCharArray().toSet(); newGroup = false
        currGroup = currGroup.intersect(line.toCharArray().toSet())
    }
    // finally:
    runningCount += currGroup.size

    println("Running count (final): $runningCount")
}
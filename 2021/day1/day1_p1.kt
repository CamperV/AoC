import java.io.File

fun main() {
	val intLines: List<Int> = File("input.txt").readLines().map{ it.toInt() }

	var prevLine = intLines[0]
	val diffList = mutableListOf<Int>()

	// drop the first line because nggak ada diff, and prevLine is already assigned to intLines[0]
	for (line in intLines.drop(1)) {
		diffList.add(line - prevLine)
		prevLine = line
	}

	val numIncreases = diffList.filter{ it > 0 }.size
	println("Number of increases in depth: $numIncreases")
}
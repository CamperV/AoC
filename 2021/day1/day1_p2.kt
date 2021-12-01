import java.io.File

fun main() {
	val intLines: List<Int> = File("input.txt").readLines().map{ it.toInt() }
	
	val compressedDepths = intLines.windowed(size=3, step=1).map{ it.sum() }
	val diffList = diffElements(compressedDepths)

	val numIncreases = diffList.filter{ it > 0 }.size
	println("Number of increases in depth: $numIncreases")
}

fun diffElements(toDiff: List<Int>): List<Int> {
	var prevLine = toDiff[0]
	val retVal = mutableListOf<Int>()

	// drop the first line because nggak ada diff, and prevLine is already assigned to intLines[0]
	for (line in toDiff.drop(1)) {
		retVal.add(line - prevLine)
		prevLine = line
	}
	return retVal
}
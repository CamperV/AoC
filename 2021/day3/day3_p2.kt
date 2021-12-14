import java.io.File
import java.util.Collections

fun main() {
	val lines = File("input.txt").readLines()

	// gamma is the most common bit for each position
	// epsilon is the least common bit for each position
	// most common bit in position 3 = gamma[2]
	val (o2Gen, co2Scrub) = filterRating(lines)
	println("$o2Gen, $co2Scrub")
	println("ans: ${o2Gen*co2Scrub}")
}

fun counter(iterable: List<String>): List<Map<Char, Int>> {
	var returnCount = mutableListOf<MutableMap<Char, Int>>()
	for (i in 0..(iterable[0].length-1)) {
		returnCount.add(
			mutableMapOf<Char, Int>('0' to 0, '1' to 0)
		)
	}

	for (line in iterable) {
		// println("Processing line $line")

		for ((ind, ch) in line.withIndex()) {
			returnCount[ind][ch] = (returnCount[ind][ch] ?: 0) + 1
		}
	}

	return returnCount
}

fun filterRating(iterable: List<String>): Pair<Int, Int> {
	var generatorList: List<String> = iterable;
	var scrubberList: List<String> = iterable;

	// recalculate Gamma each time you filter the list
	for (bitPosition in 0..(iterable[0].length-1)) {

		val countMap = counter(generatorList)
		val (gamma, _) = modRates(countMap)

		generatorList = generatorList.filter{ it[bitPosition] == gamma[bitPosition] }
		println("Final list bitPosition $bitPosition = $generatorList")

		if (generatorList.size == 1) break
	}

	for (bitPosition in 0..(iterable[0].length-1)) {

		val countMap = counter(scrubberList)
		val (_, epsilon) = modRates(countMap)

		scrubberList = scrubberList.filter{ it[bitPosition] == epsilon[bitPosition] }
		println("Final list bitPosition $bitPosition = $scrubberList")

		if (scrubberList.size == 1) break
	}

	return Pair(generatorList[0].toInt(2), scrubberList[0].toInt(2))
}

fun modRates(countMap: List<Map<Char, Int>>): Pair<String, String> {
	var gamma: String = ""
	var epsilon: String = ""

	for (countData in countMap) {
		var minChar: Char = '0'
		var maxChar: Char = '0'
		var min = Int.MAX_VALUE
		var max = Int.MIN_VALUE

		for ((ch, cnt) in countData) {
			if (cnt < min) {
				minChar = ch
				min = cnt
			}
			if (cnt > max) {
				maxChar = ch
				max = cnt
			}
		}

		epsilon = epsilon + if (min == max) '0' else minChar
		gamma = gamma + if (min == max) '1' else maxChar
	}

	return Pair(gamma, epsilon)
}
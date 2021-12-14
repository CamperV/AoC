import java.io.File
import java.util.Collections

fun main() {
	val lines = File("input.txt").readLines()

	var globalCount = mutableListOf<MutableMap<Char, Int>>()
	for (i in 0..(lines[0].length-1)) {
		globalCount.add(
			mutableMapOf<Char, Int>('0' to 0, '1' to 0)
		)
	}

	for (line in lines) {
		println("Processing line $line")

		for ((ind, ch) in line.withIndex()) {
			globalCount[ind][ch] = (globalCount[ind][ch] ?: 0) + 1

			val myVal = globalCount[ind]
			println("Added $ch to $ind :: $myVal")
		}
	}

	println(globalCount)
	val (gamma, epsilon) = rates(globalCount)

	println("Gamma: $gamma")
	println("Epsilon: $epsilon")
	println("ans: ${gamma*epsilon}")
}

fun rates(countMap: List<Map<Char, Int>>): Pair<Int, Int> {
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

		epsilon = epsilon + minChar
		gamma = gamma + maxChar
	}

	return Pair(gamma.toInt(2), epsilon.toInt(2))
}
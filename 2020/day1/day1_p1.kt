// my first Kotlin!
// let the lessons come from actually doing the exercises

import java.io.File

fun main() {
	val fileName = "input.txt"
	val lines: List<String> = File(fileName).readLines()
	//
	val nums = lines.map { line -> line.toInt() }

	for ((index, num) in nums.withIndex()) {
		
		for (other in nums.slice(index..nums.size-1)) {
			if (num + other == 2020) {
				println(num*other)
			}
		}
	}
}

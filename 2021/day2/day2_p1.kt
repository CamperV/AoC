import java.io.File

fun main() {
	val lines = File("input.txt").readLines()

	var depth = 0
	var hPosition = 0

	for (line in lines) {
		val cmd = line.split(" ")
		val command = cmd[0]
		val value = cmd[1].toInt()

		when (command) {
			"forward" -> {
				hPosition += value
			}
			"up" -> {
				depth -= value
			}
			"down" -> {
				depth += value
			}
			else -> {
				println("Failed to parse command $cmd")
			}
		}
	}

	println("Found final h: $hPosition, and depth: $depth. Ans: ${hPosition*depth}")
}
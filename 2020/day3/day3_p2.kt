import java.io.File

fun main() {
	// read in input, map either a 2D array or dictionary
	// make array
	// this is probably not the most idiomatic way to do this, but hey I'm learning
	val lines = File("input.txt").readLines()
	var map2D = Array(lines.size, { Array(lines.first().length, { 0 }) })

	for ((row_ind, row) in lines.withIndex()) {
		for ((col_ind, col) in row.withIndex()) {
			map2D[row_ind][col_ind] = if (col == '#') 1 else 0
		}
	}

	// part 1
	val start = Pair(0, 0)
	val slope = Pair(1, 3) // +row, +col
	val path = getPath(start, slope, map2D)

	val treeCount = path.count { it == 1 }
	println("Path $slope encountered $treeCount trees")

	val slopes = listOf(Pair(1, 1), Pair(1, 3), Pair(1, 5), Pair(1, 7), Pair(2, 1))
	var counts: MutableList<Int> = mutableListOf()

	for (slope in slopes) {
		val path = getPath(start, slope, map2D)
		counts.add(path.count { it == 1 })
	}

	val total_mult = counts.reduce { a, b -> a*b }
	
	println("here are the counts: ")
	counts.forEach{ println(it) }

	println("total mult: $total_mult")
}

fun getPath(start: Pair<Int, Int>, slope: Pair<Int, Int>, map2D: Array<Array<Int>>): MutableList<Int> {
	//map2D is row, col
	var path: MutableList<Int> = mutableListOf()
	path.add(map2D[start.first][start.second])

	val mapHeight = map2D.size
	val mapWidth  = map2D[0].size

	println("mapDimensions: $mapHeight rows, $mapWidth cols")

	var curr = start
	while(curr.first < mapHeight-1) {
		curr = Pair(curr.first+slope.first, (curr.second+slope.second) % (mapWidth))
		path.add(map2D[curr.first][curr.second])
	}
	return path
}

fun printMap(map2D: Array<Array<Int>>) {
	map2D.forEach { it.forEach { print(it) }; println() }
}

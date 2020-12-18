import java.io.File

fun main() {
    val initState: List<List<Char>> = File("input.txt").readLines().map{ it.toList() }

    // Rules: (this is like Conways's GoL)
    // 1) if L and numAdjacent = 0: -> #
    // 2) if # and numAdjacent > 3: -> L
    // 3) that's it
    var currState = mutateState(initState)

    var prevHash = -1
    var numTransforms = 1

    while(currState.hashCode() != prevHash) {
        numTransforms++

        prevHash = currState.hashCode()
        currState = mutateState(currState)
    }

    val numOccupied = currState.flatten().count{ it == '#' }
    println("After $numTransforms transforms, counted $numOccupied occupied seats.")
}

// permute all at once, don't let currently mutating seats affect others
fun mutateState(initState: List<List<Char>>): MutableList<MutableList<Char>> {
    val newState = mutableListOf<MutableList<Char>>()
    for ((ir, row) in initState.withIndex()) {

        var newRow = mutableListOf<Char>()
        for ((ic, curr) in row.withIndex()) {
            // get numAdjacent (N, NE, E, SE, S, SW, W, NW)
            val currPos = Pair(ir, ic)
            val numAdjacent = listOf(
                occupiedInDirection(initState, currPos, Pair(-1,  0)),   // n
                occupiedInDirection(initState, currPos, Pair(-1,  1)),   // ne
                occupiedInDirection(initState, currPos, Pair( 0,  1)),   // e
                occupiedInDirection(initState, currPos, Pair( 1,  1)),   // se
                occupiedInDirection(initState, currPos, Pair( 1,  0)),   // s
                occupiedInDirection(initState, currPos, Pair( 1, -1)),   // sw
                occupiedInDirection(initState, currPos, Pair( 0, -1)),   // w
                occupiedInDirection(initState, currPos, Pair(-1, -1))   // nw
            ).count{ it == true }

            // do something about it
            when (curr) {
                'L' -> {
                    newRow.add(if (numAdjacent == 0) '#' else 'L')
                }
                '#' -> {
                    newRow.add(if (numAdjacent > 4) 'L' else '#')
                }
                else -> {
                    newRow.add(curr)
                }
            }
        }
        newState.add(newRow)
    }

    return newState
}

fun print2D(input: List<List<Char>>) {
    input.forEach{
        println(it)
    }
}

fun occupiedInDirection(currState: List<List<Char>>, initPosition: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
    var currPos = Pair(initPosition.first+direction.first,
                       initPosition.second+direction.second)

    while(inBounds(currState, currPos)) {
        //println("> $currPos in bounds, ${currState[currPos.first][currPos.second]}")
        when (currState[currPos.first][currPos.second]) {
            'L' -> return false
            '#' -> return true
        }
        currPos = Pair(currPos.first+direction.first,
                       currPos.second+direction.second)
    }

    // else if we only see floor
    return false
}

fun inBounds(currState: List<List<Char>>, position: Pair<Int, Int>): Boolean {
    return position.first > -1 && position.first < currState.size &&
           position.second > -1 && position.second < currState[0].size
}
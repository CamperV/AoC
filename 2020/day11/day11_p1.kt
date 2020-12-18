import java.io.File

fun main() {
    val initState: List<List<Char>> = File("input.txt").readLines().map{ it.toList() }

    // Rules: (this is like Conways's GoL)
    // 1) if L and numAdjacent = 0: -> #
    // 2) if # and numAdjacent > 3: -> L
    // 3) that's it
    print2D(initState)
    var currState = mutateState(initState)
    //println("transformed: 1")
    //print2D(currState)

    var prevHash = -1
    var numTransforms = 1

    while(currState.hashCode() != prevHash) {
        numTransforms++

        prevHash = currState.hashCode()
        currState = mutateState(currState)

        //println("Transformed: $numTransforms")
        //print2D(currState)
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
            val n  = if (ir > 0) initState[ir-1][ic] else '.'
            val ne = if (ir > 0 && ic < row.size-1) initState[ir-1][ic+1] else '.'
            val e  = if (ic < row.size-1) initState[ir][ic+1] else '.'
            val se = if (ic < row.size-1 && ir < initState.size-1) initState[ir+1][ic+1] else '.'
            val s  = if (ir < initState.size-1) initState[ir+1][ic] else '.'
            val sw = if (ir < initState.size-1 && ic > 0) initState[ir+1][ic-1] else '.'
            val w  = if (ic > 0) initState[ir][ic-1] else '.'
            val nw = if (ic > 0 && ir > 0) initState[ir-1][ic-1] else '.'
            val numAdjacent = listOf(n, ne, e, se, s, sw, w, nw).count{ it == '#' }

            // do something about it
            when (curr) {
                'L' -> {
                    newRow.add(if (numAdjacent == 0) '#' else 'L')
                }
                '#' -> {
                    newRow.add(if (numAdjacent > 3) 'L' else '#')
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
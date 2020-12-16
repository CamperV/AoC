import java.io.File

class Node(joltage: Int) {
    val joltage = joltage
    var children = mutableSetOf<Int>()

    fun addChildren(fromList: List<Int>) {
        // for each index, begin to look ahead, while you can
        // max look ahead should be 3
        for (lookAhead in fromList) {
            if ((lookAhead - joltage) <= 3) {
                children.add(lookAhead)
                //println("> $joltage added $lookAhead as a child")
            }
        }
    }
}

fun main() {
    // just frickin sort the entire thing first
    // since we know we have to use all of the adaptors... just count the gaps
    val preSorted = listOf(0) + File("input.txt").readLines().map{ it.toInt() }.sorted()
    val sortedJoltage = preSorted + listOf(preSorted.last()+3)
    val numAdapt = sortedJoltage.size

    // count the number of branching paths at EACH index, then multiply them together
    var tree = mutableMapOf<Int, Node>()

    println("building tree")
    for ((index, joltage) in sortedJoltage.withIndex()) {
        // create a tree node
        var currNode = if (tree.contains(joltage)) tree[joltage]!! else Node(joltage)

        // all all possible children
        // naively add all duplicates
        // this will be inefficient
        currNode.addChildren(sortedJoltage.slice(index+1..numAdapt-1))
        tree[joltage] = currNode
    }

    var pathSums = mutableMapOf<Int, Long>()
    val sum = numPathsFrom(0, tree, pathSums)

    println("Found $sum paths")
}

fun numPathsFrom(joltage: Int, tree: MutableMap<Int, Node>, pathSums: MutableMap<Int, Long>): Long {
    // count unique paths in tree
    // DON'T do it this way... too big
    val currNode = tree[joltage]!!
    var mySum = 0L

    if (currNode.children.isEmpty()) return 1

    for (child in currNode.children) {
        if (pathSums.contains(child)) {
            mySum += pathSums[child]!!
        } else {
            mySum += numPathsFrom(child, tree, pathSums)
        }
    }
    //println("Node $joltage added their sum $mySum")
    pathSums[joltage] = mySum
    return mySum
}
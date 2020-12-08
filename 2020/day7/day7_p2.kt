// okay, so... this is a heavier one for sure
// create a quick parser to grab every bag-related rule
// then, create a graph with the bags
// hopefully, we won't need cycle detection, but we probably actually will
// practice with the example first!

// create a Bag object and a graph that preserves knowledge of a) what a bag holds and b) what it is contained in?
import java.io.File

class Bag(color: String) {
    val color = color
    var contains: MutableMap<String, Int>
    var containedBy: MutableSet<Bag>

    init {
        contains = mutableMapOf<String, Int>()
        containedBy = mutableSetOf<Bag>()
    }

    fun addChild(color: String, num: Int) {
        if (contains.containsKey(color)) {
            println("throw a stink! $color already exists in $contains")
        }
        contains[color] = num
    }

    fun addParent(bag: Bag) {
        containedBy.add(bag)
    }
}

fun main() {
    var bagMap = mutableMapOf<String, Bag>()

    for (rule in File("input.txt").readLines()) {
        // vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
        // faded blue bags contain no other bags.
        // {type} bags contain {num} {type} bags.
        // {type} bags contain {type} bags.
        val newBag = parseRule(rule)
        bagMap[newBag.color] = newBag
    }

    // associate parents
    for ((color, bag) in bagMap) {
        for ((childColor, numChild) in bag.contains) {
            bagMap[childColor]!!.addParent(bag)
        }
    }

    // pop this out of the function, I'm tired
    var runningCount = 0
    var queue = mutableListOf<Bag>(bagMap["shiny gold"]!!)

    while (!queue.isEmpty()) {
        val curr = queue.removeAt(0)

        for ((childColor, childNum) in curr.contains) {
            val currCol = curr.color

            for (i in 1..childNum) {
                queue.add(bagMap[childColor]!!)
                runningCount++
            }
        }
    }
    println("shiny gold contains $runningCount bags")
}

fun parseRule(rule: String): Bag {
    val typeList = rule.split(" bags contain ") // this will always return a list(2), otherwise it is a malformed rule
    val bagColor = typeList[0]
    val predicate = typeList[1].split(",").map{ it.trim() }

    var bag = Bag(bagColor)
    for (pred in predicate) {
        val fmtPred = pred.slice(0..pred.indexOf(" bag")-1) // trim "bag/bags/bags." out
        //
        val numberOf = if (fmtPred.startsWith("no other")) 0 else fmtPred.filter{ it.isDigit() }.trim().toInt()

        if (numberOf > 0) {
            val colorOf = fmtPred.filter{ !it.isDigit() }.trim()
            bag.addChild(colorOf, numberOf)
        }
    }
    return bag
}

fun countContainersIter(bag: Bag): Int {
    var parentSet = mutableSetOf<Bag>()
    var queue = mutableListOf<Bag>(bag)

    while (!queue.isEmpty()) {
        val curr = queue.removeAt(0)

        curr.containedBy.forEach{
            parentSet.add(it)
            queue.add(it)
        }
    }
    return parentSet.size
}
import java.io.File;

fun main() {
    val lines = File("input.txt").readLines()
    val readyAt = lines[0].toInt()
    val presentBuses = lines[1].split(",").filter{ it != "x" }.map{ it.toInt() }

    println("Got present busses: ${presentBuses}")
    var busReadyAt = mutableListOf<Pair<Int, Int>>()

    // starting at 0, create all times the bus is at the stop
    for (bus in presentBuses) {
        var currTime = 0
        while (currTime < readyAt) {
            currTime += bus
        }
        busReadyAt.add(Pair(bus, currTime))
    }

    // now that we know when the busses will be here, find the smallest
    val bestBusPair = busReadyAt.minByOrNull{ it.second }!!
    val ans: Int = bestBusPair.first * (bestBusPair.second - readyAt)
    println("Best time for $readyAt: Bus #${bestBusPair}: ${ans}")
}
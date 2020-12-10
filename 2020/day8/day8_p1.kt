import java.io.File

class StateMachine() {
    var acc = 0
    var currIns = 0
    var calledIns = mutableSetOf<Int>()

    fun run(instructions: List<String>) {
        var dontBreak = true

        while (dontBreak) {
            val insStr = instructions[currIns].split(" ")
            val ins = insStr[0]
            val arg = insStr[1]

            println("executing $insStr")
            dontBreak = executeIns(ins, arg.toInt())
        }
    }

    fun executeIns(ins: String, arg: Int): Boolean {
        // break condition calculated here
        if (currIns in calledIns) return false
        calledIns.add(currIns)
        println("just added $currIns:$ins to calledIns ($acc)")

        when(ins) {
            "acc" -> {
                acc += arg
                currIns++
            }
            "jmp" -> {
                currIns += arg
                println("currIns now $currIns ($arg)")
            }
            else -> {
                currIns++
            }
        }

        return true
    }
}

fun main() {
    val instructions = File("input.txt").readLines()

    var sm = StateMachine()
    sm.run(instructions)

    val acc = sm.acc
    println("After break condition has been reached, the acc value is $acc")
}
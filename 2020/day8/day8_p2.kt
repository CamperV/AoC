import java.io.File

class StateMachine() {
    var acc = 0
    var currIns = 0
    var calledIns = mutableSetOf<Int>()

    fun run(instructions: List<String>): Boolean {
        var normalOp = true

        while (normalOp && currIns < instructions.size) {
            val insStr = instructions[currIns].split(" ")
            val ins = insStr[0]
            val arg = insStr[1]

            normalOp = executeIns(ins, arg.toInt())
        }
        return normalOp
    }

    fun executeIns(ins: String, arg: Int): Boolean {
        // break condition calculated here
        if (currIns in calledIns) return false
        calledIns.add(currIns)

        when(ins) {
            "acc" -> {
                acc += arg
                currIns++
            }
            "jmp" -> {
                currIns += arg
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

    // get the locations at which to modify the instructions
    var swapIndices = mutableListOf<Int>()
    instructions.withIndex().forEach{
        (index, line) -> run {
            if (line.startsWith("jmp") || line.startsWith("nop")) {
                swapIndices.add(index)
            }
        }
    }

    // run for all permutations
    for (insIndex in swapIndices) {
        val newInstructions = instructions.withIndex().map{
            (ind, ins) -> if (ind == insIndex) swapIns(ins) else ins
        }

        var sm = StateMachine()
        val exitedNormally = sm.run(newInstructions)

        if (exitedNormally) {
            val acc = sm.acc
            println("After exiting: $exitedNormally, the acc value is $acc")
            break
        }
        
    }    
}

fun swapIns(ins: String): String {
    if (ins.startsWith("nop")) {
        return ins.replace("nop", "jmp")
    } else if (ins.startsWith("jmp")) {
        return ins.replace("jmp", "nop")
    } else {
        println("invalid input $ins")
        return ins
    }
}
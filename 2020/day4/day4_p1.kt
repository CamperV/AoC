import java.io.File

fun Bool Int.within(lower: Int, upper: Int) {
    return this >= lower && this <= upper
}

fun main() {
    // create the dictionary first
    val lines = File("input.txt").readLines()
    val passports: MutableList<MutableMap<String, String>> = mutableListOf()

    var currentPassport = createNewPassport()
    for (line in lines) {
        if (line.isBlank()) {
            passports.add(currentPassport)
            currentPassport = createNewPassport()
            continue
        }

        line.trimEnd().split(" ").forEach{
            val pair = it.split(":")
            currentPassport.put(pair[0], pair[1])
        }

        if (line == lines.last()) passports.add(currentPassport)
    }

    // now count valid passports
    val mandatoryValidSet = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    val numPassports = passports.size
    val numValid = passports.filter{ it.keys.containsAll(mandatoryValidSet) }.size
    println("Found $numValid valid passports of $numPassports total passports")
}

fun createNewPassport(): MutableMap<String, String> {
    return mutableMapOf<String, String>()
}
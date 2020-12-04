import java.io.File

// extensions
fun Int.within(lower: Int, upper: Int): Boolean {
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
        // don't forget the last passport
        if (line == lines.last()) passports.add(currentPassport)
    }

    // now count valid passports
    val mandatoryValidSet = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    val numPassports = passports.size
    val numValid = passports.filter{ isPassportValid(it, mandatoryValidSet) }.size
    println("Found $numValid valid passports of $numPassports total passports")
}

fun createNewPassport(): MutableMap<String, String> {
    return mutableMapOf<String, String>()
}

fun isPassportValid(passport: MutableMap<String, String>, validSet: Set<String>): Boolean {
    if (passport.keys.containsAll(validSet)) {
        passport.keys.forEach{
            val value = fieldValid(passport, it)
            val entry = passport[it]
            println("$it ($entry) returned $value")
        }
    }
    return passport.keys.containsAll(validSet) && passport.keys.all{ fieldValid(passport, it) }
}

fun fieldValid(passport: MutableMap<String, String>, field: String): Boolean {
    when(field) {
        "byr" -> {
            val byr = passport["byr"]!!
            return byr.length == 4 && byr.toInt().within(1920, 2002)
        }
        "iyr" -> {
            val iyr = passport["iyr"]!!
            return iyr.length == 4 && iyr.toInt().within(2010, 2020)
        }
        "eyr" -> {
            val eyr = passport["eyr"]!!
            return eyr.length == 4 && eyr.toInt().within(2020, 2030)
        }
        "hgt" -> {
            val hgt = passport["hgt"]!!
            if (hgt.endsWith("cm")) {
                return hgt.slice(0..hgt.length-3).toInt().within(150, 193)
            }
            else if (hgt.endsWith("in")) {
                return hgt.slice(0..hgt.length-3).toInt().within(59, 76)
            } else {
                return false;
            }
        }
        "hcl" -> {
            val hcl = passport["hcl"]!!
            if (hcl.startsWith("#")) {
                return "[0-9a-f]+".toRegex().matches(hcl.slice(1..6))
            } else {
                return false;
            }
        }
        "ecl" -> {
            val ecl = passport["ecl"]!!
            return setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(ecl.slice(0..2))
        }
        "pid" -> {
            var pid = passport["pid"]!!
            return pid.length == 9 && "[0-9]+".toRegex().matches(pid)
        }

        // pass-over
        "cid" -> return true;
    }
    return false;
}
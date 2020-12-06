import java.io.File

fun main(args : Array<String>) {
    var countContainsFields = 0
    var countValidData = 0
    var map = hashMapOf<String, String>()
    File("src/main/resources/data.4.txt").forEachLine {
        if (it.trim().isEmpty()) {
            if (hasValidFields(map)) countContainsFields++
            if (hasValidData(map)) countValidData++
            map.clear()
        }
        else {
            val fields = it.split(' ')
            fields.forEach() {
                val values = it.split(':')
                map.put(values.first(), values.last())
            }
        }
    }

    println("Count valid fields: $countContainsFields")
    println("Count valid data: $countValidData")
}

fun hasValidFields(map : HashMap<String, String>) : Boolean {
    val requiredKeys = arrayListOf<String>("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    requiredKeys.forEach { if (!map.containsKey(it)) return false }
    return true
}

fun hasValidData(map : HashMap<String, String>) : Boolean {
    /*
    byr (Birth Year) - four digits; at least 1920 and at most 2002.
    iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    hgt (Height) - a number followed by either cm or in:
    If cm, the number must be at least 150 and at most 193.
    If in, the number must be at least 59 and at most 76.
    hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
    ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
    pid (Passport ID) - a nine-digit number, including leading zeroes.
    cid (Country ID) - ignored, missing or not.
     */

    if (!hasValidFields(map)) return false

    if (!isValidYear(map["byr"].toString(), 1920, 2002)) return false
    if (!isValidYear(map["iyr"].toString(), 2010, 2020)) return false
    if (!isValidYear(map["eyr"].toString(), 2020, 2030)) return false

    if (!map["hgt"].toString().matches("^((1[5-8][0-9]|19[0-3])cm|(59|6[0-9]|7[0-6])in)\$".toRegex())) return false
    if (!map["hcl"].toString().matches("^#[(0-9)|(a-f)]{6}\$".toRegex())) return false
    if (map["ecl"].toString() !in arrayListOf<String>("amb", "blu", "brn", "gry", "grn", "hzl", "oth")) return false
    if (!map["pid"].toString().matches("^[0-9]{9}\$".toRegex())) return false

    return true
}

fun isValidYear(input : String, min : Int, max : Int) : Boolean {
    return !input.isNullOrEmpty() && input.length == 4 && input.toInt() in min..max
}
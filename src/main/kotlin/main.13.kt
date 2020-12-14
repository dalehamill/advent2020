import java.io.File

fun main(args: Array<String>) {
    var input = arrayListOf<String>()
    File("src/main/resources/data.13.txt").forEachLine {
        input.add(it)
    }

    // PART 1
    println("Earliest bus calculation: ${earliestBus(input)}")
}

fun earliestBus(input: ArrayList<String>): Long {
    val depart = input[0].toLong()
    val busses = input[1]
        .split(',')
        .filter { it != "x" }

    val busNumbers = busses.map { it.toInt() }
    var minIndex = -1
    var minValue = depart + 1
    for (i in busNumbers.indices) {
        var nextBus = (depart % busNumbers[i])
        if (nextBus > 0) nextBus = busNumbers[i] - nextBus
        if (nextBus < minValue) {
            minValue = nextBus
            minIndex = i
        }
    }
    println("Earliest bus is number: ${busNumbers[minIndex]}")

    return ((((depart / busNumbers[minIndex]) + 1) * busNumbers[minIndex]) - depart) * busNumbers[minIndex]
}
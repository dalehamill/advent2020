import java.io.File

fun main(args: Array<String>) {
    var input = arrayListOf<String>()
    File("src/main/resources/data.13.txt").forEachLine {
        input.add(it)
    }

    // PART 1
    //println("Earliest bus calculation: ${earliestBus(input)}")

    // PART 2
    println("Earliest timestamp: ${earliestTimestamp(input)}")
}

fun earliestTimestamp(input: ArrayList<String>): Long {
    val busses = input[1]
        .split(',')

    var t = 0L
    var increment = 0L
    for (i in busses.indices.reversed()) {
        if (busses[i] == "x") continue
        val bus = busses[i].toLong()

        if (increment == 0L) {
            increment = bus
            t = bus - i.toLong()
            continue
        }

        while ((t + i) % bus != 0L) {
            t += increment
        }
        increment *= bus
        println("index $i, time $t, increment: $increment")
    }

    return t
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


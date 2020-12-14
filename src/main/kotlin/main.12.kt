import java.io.File
import java.lang.Math.abs

fun main(args: Array<String>) {
    var input = arrayListOf<String>()
    File("src/main/resources/data.12.txt").forEachLine {
        input.add(it)
    }

    // PART 1
    println("Manhattan Distance: ${manhattanDistance(input)}")

    // PART 2
    println("Manhattan Distance 2: ${manhattanDistanceWithWaypoint(input)}")
}

fun manhattanDistance(input: ArrayList<String>): Long {
    var currDirection = 'E'
    var northDist = 0L
    var eastDist = 0L
    for (nav in input) {
        var cmd = nav[0]
        val dst = nav.substring(1).toInt()

        if (cmd == 'L' || cmd == 'R') currDirection = simpleTurnRight(currDirection, (if (cmd == 'L') -1 else 1) * dst)
        else {
            cmd = if (cmd == 'F') currDirection else cmd
            if (cmd == 'N') northDist += dst
            if (cmd == 'S') northDist -= dst
            if (cmd == 'E') eastDist += dst
            if (cmd == 'W') eastDist -= dst
        }
    }

    return abs(northDist) + abs(eastDist)
}

fun manhattanDistanceWithWaypoint(input: ArrayList<String>): Long {
    // E, N
    var waypointE = 10L
    var waypointN = 1L
    var locationE = 0L
    var locationN = 0L

    for (nav in input) {
        var cmd = nav[0]
        val dst = nav.substring(1).toInt()

        when (cmd) {
            'E' -> waypointE += dst
            'W' -> waypointE -= dst
            'N' -> waypointN += dst
            'S' -> waypointN -= dst
            'R' -> {
                val moves = dst / 90
                for (m in 0 until moves) {
                    var tmp = waypointE
                    waypointE = waypointN
                    waypointN = tmp * -1
                }
            }
            'L' -> {
                val moves = dst / 90
                for (m in 0 until moves) {
                    var tmp = waypointE
                    waypointE = waypointN * -1
                    waypointN = tmp
                }
            }
            'F' -> {
                locationE += waypointE * dst
                locationN += waypointN * dst
            }
        }
    }

    return abs(locationE) + abs(locationN)
}

fun simpleTurnRight(dir: Char, deg: Int): Char {
    val moves = deg / 90
    val arr = arrayListOf('N', 'E', 'S', 'W')
    val startingIndex = arr.indexOf(dir)
    var finalIndex = (startingIndex + moves) % arr.size
    while (finalIndex < 0) finalIndex += arr.size
    return arr[finalIndex]
}
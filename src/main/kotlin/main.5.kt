import java.io.File
import kotlin.math.max

fun main(args: Array<String>) {
    val seatSet = hashSetOf<Int>()
    var highestSeatId = 0
    File("src/main/resources/data.5.txt").forEachLine {
        var rowmin = 0
        var rowmax = 127
        var colmin = 0
        var colmax = 7

        it.forEach {
            val rowmid = ((rowmax - rowmin) / 2) + rowmin
            val colmid = ((colmax - colmin) / 2) + colmin
            if (it == 'B') {
                // upper half
                rowmin = rowmid + 1
            } else if (it == 'F') {
                // lower half
                rowmax = rowmid
            } else if (it == 'R') {
                // upper half
                colmin = colmid + 1
            } else if (it == 'L') {
                // lower half
                colmax = colmid
            }
        }

        val seatId = (rowmin * 8) + colmin
        println("Input $it maps to Row: $rowmin, Col: $colmin, and Seat ID: $seatId")

        // calculate highest seat id
        highestSeatId = max(highestSeatId, seatId)

        // add to hash for identifying my seat
        seatSet.add(seatId)
    }

    var mySeatId = 0
    (0..(127*8 + 7)).forEach() {
        if (!seatSet.contains(it) && seatSet.contains(it + 1) && seatSet.contains(it - 1)) {
            mySeatId = it
            return@forEach
        }
    }

    println("Highest seat ID: $highestSeatId")
    println("My seat ID: $mySeatId")
}
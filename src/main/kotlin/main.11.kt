import java.io.File

fun main(args: Array<String>) {
    var input = arrayListOf<String>()
    File("src/main/resources/data.11.txt").forEachLine {
        input.add(it)
    }

    // PART 1

    var inputState: java.util.ArrayList<String>
    var transformedState = input
    do {
        inputState = transformedState
        transformedState = applyRules(inputState)
//        printState(transformedState)
    } while (transformedState != inputState)

    var cntOccupied = 0
    for (row in transformedState) {
        for (seat in row) {
            if (seat == '#') cntOccupied++
        }
    }
    println("Seats Occupied Limited: $cntOccupied")

    // PART 2
    transformedState = input
    do {
        inputState = transformedState
        transformedState = applyRules(inputState, true)
        printState(transformedState)
    } while (transformedState != inputState)

    cntOccupied = 0
    for (row in transformedState) {
        for (seat in row) {
            if (seat == '#') cntOccupied++
        }
    }
    println("Seats Occupied Unlimited: $cntOccupied")
}

fun applyRules(input : ArrayList<String>, unlimited : Boolean = false) : ArrayList<String> {
    val output = arrayListOf<String>()

    for (r in 0 until input.size) {
        val inputLine = input[r]
        var outputLine = ""
        for (c in inputLine.indices) {
            var ch = inputLine[c]
            if (ch == 'L') {
                // check
                if (!unlimited && checkOccupacyAdjacent(input, r, c, 1)) ch = '#'
                if (unlimited && checkOccupacyUnlimited(input, r, c, 1)) ch = '#'
            }
            else if (ch == '#') {
                // check
                if (!unlimited && !checkOccupacyAdjacent(input, r, c, 4)) ch = 'L'
                if (unlimited && !checkOccupacyUnlimited(input, r, c, 5)) ch = 'L'
            }
            outputLine += ch
        }
        output.add(outputLine)
    }
    return output
}

private fun checkOccupacyUnlimited(input: ArrayList<String>, r: Int, c: Int, maxOcc: Int) : Boolean {
    val maxRows = input.size
    val maxCols = input.first().length

    var cntOccupied = 0
    // up
    for (rr in (r - 1) downTo 0) {
        if (rr < 0) break
        if (input[rr][c] == '#') cntOccupied++
        if (input[rr][c] != '.') {
            break
        }
    }
    // down
    for (rr in (r + 1) until maxRows) {
        if (input[rr][c] == '#') cntOccupied++
        if (input[rr][c] != '.') {
            break
        }
    }
    // left
    for (cc in (c - 1) downTo 0) {
        if (cc < 0) break
        if (input[r][cc] == '#') cntOccupied++
        if (input[r][cc] != '.') {
            break
        }
    }
    // right
    for (cc in (c + 1) until maxCols) {
        if (input[r][cc] == '#') cntOccupied++
        if (input[r][cc] != '.') {
            break
        }
    }
    // diag up/left
    for (offset in 1..c) {
        val rr = r - offset
        val cc = c - offset
        if (rr < 0) break
        if (input[rr][cc] == '#') cntOccupied++
        if (input[rr][cc] != '.') {
            break
        }
    }
    // diag down/right
    for (offset in 1 until maxCols - c) {
        val rr = r + offset
        val cc = c + offset
        if (rr >= maxRows) break
        if (input[rr][cc] == '#') cntOccupied++
        if (input[rr][cc] != '.') {
            break
        }
    }
    // diag up/right
    for (offset in 1 until maxCols - c) {
        val rr = r - offset
        val cc = c + offset
        if (rr < 0) break
        if (input[rr][cc] == '#') cntOccupied++
        if (input[rr][cc] != '.') {
            break
        }
    }
    // diag down/left
    for (offset in 1..c) {
        val rr = r + offset
        val cc = c - offset
        if (rr >= maxRows) break
        if (input[rr][cc] == '#') cntOccupied++
        if (input[rr][cc] != '.') {
            break
        }
    }

    return cntOccupied < maxOcc
}

private fun checkOccupacyAdjacent(input: ArrayList<String>, r: Int, c: Int, maxOcc: Int) : Boolean {
    val maxRows = input.size
    val maxCols = input.first().length

    var cntOccupied = 0
    for (rr in (r - 1) .. (r + 1)) {
        if (rr < 0 || rr >= maxRows) continue
        for (cc in (c - 1) .. (c + 1)) {
            if (cc < 0 || cc >= maxCols) continue
            if (rr == r && cc == c) continue
            if (input[rr][cc] == '#') {
                cntOccupied++
            }
        }
    }
    return cntOccupied < maxOcc
}

fun printState(input: ArrayList<String>) {
    println("\n---\nCurrent state: \n\n${input.joinToString(separator = "\n")}\n---\n")
}


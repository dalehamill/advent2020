import java.io.File

val SOLUTION_MAP = hashMapOf<Int, Long>()

fun main(args: Array<String>) {
    var input = arrayListOf<Int>()
    input.add(0)
    File("src/main/resources/data.10.txt").forEachLine {
        input.add(it.toInt())
    }

    input.sort()
    val allAdaptersPathStats = findAdapterPathUsingAll(input)
    println("Using all adapters, we get ${allAdaptersPathStats.first} ones and ${allAdaptersPathStats.second} threes.")
    println("Part 1 answer: ${allAdaptersPathStats.first * allAdaptersPathStats.second}")

    for (i in (input.size - 1 downTo 0)) {
        findAdapterPathDynamic(input, i)
    }
    println("Part 2 answer: ${SOLUTION_MAP[0]}")
}

fun findAdapterPathUsingAll(input: ArrayList<Int>): Pair<Int, Int> {
    var cntOnes = 0
    var cntThrees = 0
    for (i in 0 until input.size) {
        val diff = if (i == 0) input[i] - 0 else input[i] - input[i - 1]
        if (diff > 3) throw Exception("error state found")

        if (diff == 1) cntOnes++
        if (diff == 3) cntThrees++
    }
    cntThrees++ // always three higher on the last step

    return Pair(cntOnes, cntThrees)
}

fun findAdapterPathDynamic(input: ArrayList<Int>, index: Int): Long {
    if (index > input.size) {
        return 0
    }
    if (index == input.size - 1) {
        SOLUTION_MAP[index] = 1
        return 1
    }

    if (!SOLUTION_MAP.contains(index)) {
        val curr = input[index]
        var numDistinct = 0L
        for (n in (index + 3 downTo index + 1)) {
            if (n < input.size && input[n] - curr <= 3) {
                numDistinct += findAdapterPathDynamic(input, n)
            }
        }
        SOLUTION_MAP[index] = numDistinct
    }

    return SOLUTION_MAP[index] ?: 0L
}
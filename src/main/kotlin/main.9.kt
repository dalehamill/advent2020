import java.io.File
import java.lang.Long.max
import java.lang.Long.min

fun main(args: Array<String>) {
    val input = arrayListOf<Long>()
    File("src/main/resources/data.9.txt").forEachLine {
        input.add(it.toLong())
    }

    val firstException = findFirstException(input, 25)
    println("First exception: $firstException")

    println("Contiguous Range Sum: ${findContiguousSumRange(input, firstException)}")
}

fun findFirstException(input: ArrayList<Long>, preamble: Int): Long {
    for (n in (preamble until (input.size - 1))) {
        val preambleSet = hashSetOf<Long>()
        val curr = input[n]
        var valid = false
        for (p in (n - preamble) until n) {
            val pre = input[p]
            if (preambleSet.contains(pre)) {
                valid = true
                break
            }
            preambleSet.add(curr - pre)
        }
        if (!valid) return curr
    }
    return -1
}

fun findContiguousSumRange(input: ArrayList<Long>, sum: Long): Long {
    for (n in (0..input.size)) {
        var rangeSum = input[n]
        var minn = rangeSum
        var maxx = rangeSum
        for (m in ((n + 1)..input.size)) {
            val mVal = input[m]
            minn = min(minn, mVal)
            maxx = max(maxx, mVal)
            rangeSum += mVal
            if (rangeSum == sum) return minn + maxx
            if (rangeSum > sum) break
        }
    }
    return -1
}
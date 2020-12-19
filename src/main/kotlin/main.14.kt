import java.io.File
import kotlin.math.pow

fun main(args: Array<String>) {
    var mask = ""
    var values = arrayListOf<CharArray>()
    var input = arrayListOf<String>()

    var fullInput = arrayListOf<String>()
    File("src/main/resources/data.14.txt").forEachLine {
        fullInput.add(it)
    }

    fullInput.forEach {
        if (it.startsWith("mask")) mask = it.split(' ')[2]
        else {
            input.add(it)
            values.add(convertToBits(it.split(' ')[2].toLong(), mask.reversed()))

        }
    }

    // PART 1
//    part1(input, values)

    // PART 2
    val map = hashMapOf<String, Long>()
    fullInput.forEach {
        if (it.startsWith("mask")) mask = it.split(' ')[2]
        else {
            val inputLong = it.split(' ')[2].toLong()
            val inputIndex = it.split('[')[1].split(']')[0].toLong()

            val indexArray = convertToBitList(inputIndex, mask.reversed())
            for (i in indexArray.indices) {
                map[indexArray[i]] = inputLong
            }
        }
    }
    println("Step 2: ${map.values.sum()}")
}

private fun part1(
    input: ArrayList<String>,
    values: ArrayList<CharArray>
) {
    val map = hashMapOf<Int, Long>()
    for (i in input.indices) {
        val memIndex = input[i].split('[')[1].split(']')[0].toInt()
        map[memIndex] = convertToLong(values[i])
    }

    println("Step 1: ${map.values.sum()}")
}

fun convertToBits(input: Long, mask: String): CharArray {
    val output = CharArray(36)

    var value = input
    for (i in 0 until 36) {
        val half = value / 2

        if (mask[i] == 'X') output[i] = if (half * 2 == value) '0' else '1'
        else output[i] = mask[i]

        value = half
    }

    return output
}

fun convertToBitList(input: Long, mask: String): ArrayList<String> {
    var chars = convertToBits(input, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    var result = arrayListOf<String>("")

    for (i in 0 until 36) {
        val m = mask[i]

        val altered = arrayListOf<String>()
        if (m == '0') {
            for (r in result) {
                altered.add("${chars[i]}$r")
            }
        }
        else if (m == '1') {
            for (r in result) {
                altered.add("1$r")
            }
        }
        else {
            for (r in result) {
                altered.add("0$r")
                altered.add("1$r")
            }
        }
        result = altered
    }

    return result
}

fun convertToLong(input: CharArray): Long {
    var value = 0L

    for (i in input.indices) {
        if (input[i] == '1') value += 2.0.pow(i).toLong()
    }

    return value
}
import java.io.File

fun main(args: Array<String>) {
    val list = arrayListOf<Int>()
    File("src/main/resources/data.1.txt").forEachLine {
        list.add(it.toInt())
    }
    println(multOfSum(list, 2020))
    println(multOfSumOfThree(list, 2020))
}

fun multOfSum(list: List<Int>, x: Int): Int {
    var result = -1
    val map = hashMapOf<Int, Int>();
    list.forEach() {
        val n = it.toInt()
        if (map.containsKey(n)) {
            result = n
            return@forEach
        } else {
            map.put(x - n, -1)
        }
    }

    println(map)
    println("Found value $result")
    return if (result < 0) -1 else result * (x - result)
}

fun multOfSumOfThree(list: List<Int>, x: Int): Int {
    list.forEach() {
        val multOfSumWithIt = multOfSum(list, x - it)
        if (multOfSumWithIt >= 0) {
            return multOfSumWithIt * it
        }
    }
    return -1
}
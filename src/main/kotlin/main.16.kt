import java.io.File

fun main(args: Array<String>) {
    val mapValToIndex = hashMapOf<Long, Long>()
    File("src/main/resources/data.15.txt").forEachLine {
        for (n in it.split(',').map { it.toLong() }) {
            mapValToIndex.put(n, mapValToIndex.size.toLong())
        }
    }

    var index = mapValToIndex.size.toLong()
    var num = 0L

    while (index < 30000000) {
        println("Index: $index, Num: $num")

        var tmp = if (mapValToIndex.containsKey(num)) index - mapValToIndex.getOrDefault(num, 0L) else 0L
        mapValToIndex[num] = index

        index++

        if (index < 30000000) num = tmp
    }

    println("Part 1: $num")
}
